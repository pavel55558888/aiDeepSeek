package org.example.aideepseek.controller.deepseek.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.aideepseek.controller.deepseek.service.DeepSeekControllerService;
import org.example.aideepseek.database.model.HttpManualModel;
import org.example.aideepseek.database.service.http_manual.DeleteHttpManual;
import org.example.aideepseek.database.service.http_manual.GetHttpManual;
import org.example.aideepseek.database.service.http_manual.SetHttpManual;
import org.example.aideepseek.deepseek.service.DeepSeekService;
import org.example.aideepseek.dto.ParsingInstruction;
import org.example.aideepseek.dto.QuestionWithAnswers;
import org.example.aideepseek.ignite.service.task.GetTask;
import org.example.aideepseek.ignite.service.task.SetCacheTask;
import org.example.aideepseek.parser_html.ParserHtmlService;
import org.example.aideepseek.prompt.PromptDeepSeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DeepSeekControllerServiceImpl implements DeepSeekControllerService {
    private static final Logger log = LoggerFactory.getLogger(DeepSeekControllerServiceImpl.class);
    @Value("${manual.expiry.day}")
    private long manualExpiryMs;

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private GetTask getTask;

    @Autowired
    private SetCacheTask setCacheTask;

    @Autowired
    private ParserHtmlService parserHtmlService;

    @Autowired
    private DeleteHttpManual deleteHttpManual;

    @Autowired
    private SetHttpManual setHttpManual;

    @Autowired
    private GetHttpManual getHttpManual;

    private final Gson gson = new Gson();

    @Override
    public ResponseEntity<?> handleQuestionFormat(String userMessage) {
        String response = deepSeekService.getChatCompletion(userMessage);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> handleHtmlFormat(String rawHtml, String domain) {
        String cleanedHtml = parserHtmlService.removeCssAndJs(rawHtml);
        List<HttpManualModel> manuals = getHttpManual.getHttpManual(domain);

        cleanupExpiredManuals(manuals);

        ParsingInstruction instruction = null;
        List<QuestionWithAnswers> parsedQuestions = new ArrayList<>();

        for (HttpManualModel manual : manuals) {
            try {
                ParsingInstruction instr = gson.fromJson(manual.getManual(), ParsingInstruction.class);
                List<QuestionWithAnswers> questions = parserHtmlService.parseQuestions(cleanedHtml, instr);
                if (!questions.isEmpty()) {
                    instruction = instr;
                    parsedQuestions = questions;
                    break;
                }
            } catch (JsonSyntaxException e) {
                log.warn("Invalid JSON in manual ID {}: {}", manual.getId(), manual.getManual(), e);
            }
        }

        if (parsedQuestions.isEmpty()) {
            instruction = generateParsingInstruction(cleanedHtml);
            if (instruction == null) {
                return ResponseEntity.status(500).body("Failed to generate parsing instruction");
            }
            setHttpManual.setHttpManual(new HttpManualModel(domain, new Timestamp(System.currentTimeMillis()), gson.toJson(instruction)));
            parsedQuestions = parserHtmlService.parseQuestions(cleanedHtml, instruction);
        }

        if (parsedQuestions.isEmpty()) {
            return ResponseEntity.status(500).body("No questions could be extracted from HTML");
        }

        StringBuilder questionText = new StringBuilder();
        for (QuestionWithAnswers qa : parsedQuestions) {
            questionText.append(qa.getQuestion());
        }
        String fullQuestion = questionText.toString();

        String cachedAnswer = "";
        if(!fullQuestion.isEmpty()) {
            cachedAnswer = getTask.getTask(generateKeyIgniteCacheTask(parsedQuestions));
            if (cachedAnswer != null && !cachedAnswer.isBlank()) {
                return ResponseEntity.ok(cachedAnswer);
            }
        }

        String prompt = PromptDeepSeek.DEEPSEEK_PROMPT_CHOOSE_ANSWER.getPrompt() + parsedQuestions;
        String aiResponse = deepSeekService.getChatCompletion(prompt);
        log.debug("AI response for question: {} -> {}", fullQuestion, aiResponse);

        setCacheTask.setCacheTask(generateKeyIgniteCacheTask(parsedQuestions), aiResponse);

        return ResponseEntity.ok(aiResponse);
    }

    private String generateKeyIgniteCacheTask(List<QuestionWithAnswers> parsedQuestions){
        int sumLengthAllAnswer = 0;
        for (QuestionWithAnswers qa : parsedQuestions) {
            for (String str : qa.getAnswers()) {
                sumLengthAllAnswer += str.length();
            }
        }
        String key = parsedQuestions.get(0).getQuestion() + sumLengthAllAnswer;

        return key;
    }

    private void cleanupExpiredManuals(List<HttpManualModel> manuals) {
        long cutoffTime = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(manualExpiryMs);
        for (HttpManualModel manual : manuals) {
            if (manual.getTimestamp().getTime() < cutoffTime) {
                deleteHttpManual.deleteHttpManual(manual.getId());
            }
        }
    }

    private ParsingInstruction generateParsingInstruction(String html) {
        String prompt = PromptDeepSeek.DEEPSEEK_PROMPT_MANUAL.getPrompt() + html;

        String rawResponse = deepSeekService.getChatCompletion(prompt);

        String jsonPart = extractJsonFromResponse(rawResponse);
        if (jsonPart == null || jsonPart.isBlank()) {
            log.error("AI returned non-JSON response: {}", rawResponse);
            return null;
        }

        try {
            ParsingInstruction instruction = gson.fromJson(jsonPart, ParsingInstruction.class);
            log.debug("Generated parsing instruction: {}", instruction);
            return instruction;
        } catch (JsonSyntaxException e) {
            log.error("Failed to parse AI-generated JSON: {}", jsonPart, e);
            return null;
        }
    }

    private String extractJsonFromResponse(String response) {
        int start = response.indexOf('{');
        int end = response.lastIndexOf('}');
        if (start == -1 || end == -1 || start >= end) {
            return null;
        }
        return response.substring(start, end + 1);
    }
}
