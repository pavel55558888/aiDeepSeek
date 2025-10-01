package org.example.aideepseek.parser_html;

import org.example.aideepseek.deepseek.service.DeepSeekService;
import org.example.aideepseek.dto.ParsingInstruction;
import org.example.aideepseek.dto.QuestionWithAnswers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParserHtmlService {
    private static final Logger log = LoggerFactory.getLogger(ParserHtmlService.class);

    @Autowired
    private DeepSeekService deepSeekService;

    public String removeCssAndJs(String htmlContent) {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            return "";
        }

        Document doc = Jsoup.parse(htmlContent);

        Elements scripts = doc.select("script");
        scripts.remove();

        Elements styles = doc.select("style");
        styles.remove();

        Elements cssLinks = doc.select("link[rel=stylesheet], link[rel=StyleSheet], link[rel=STYLESHEET]");
        cssLinks.remove();

        Elements allElements = doc.select("*");
        for (Element element : allElements) {
            element.removeAttr("style");
        }

        String[] eventAttributes = {
                "onload", "onclick", "onmouseover", "onmouseout", "onsubmit", "onchange",
                "onfocus", "onblur", "onkeydown", "onkeyup", "onkeypress", "onerror"
        };
        for (String attr : eventAttributes) {
            for (Element element : allElements) {
                element.removeAttr(attr);
            }
        }

        return doc.html();
    }


    public List<QuestionWithAnswers> parseQuestions(String html, ParsingInstruction instruction) {
        if (html == null || instruction == null) {
            return new ArrayList<>();
        }

        Document doc = Jsoup.parse(html);

        Elements questionContainers = doc.select(instruction.getQuestionContainerSelector());

        List<QuestionWithAnswers> result = new ArrayList<>();

        for (Element container : questionContainers) {
            String questionText = extractText(container, instruction.getQuestionTextSelector(), instruction.isTextOnly());

            Elements answerElements = container.select(instruction.getAnswerSelector());
            List<String> answers = new ArrayList<>();
            for (Element answerEl : answerElements) {
                String answer = extractText(answerEl, null, instruction.isTextOnly());
                if (answer != null && !answer.trim().isEmpty()) {
                    answers.add(answer.trim());
                }
            }

            if (questionText != null && !questionText.trim().isEmpty()) {
                result.add(new QuestionWithAnswers(questionText.trim(), answers));
            }
        }

        return result;
    }

    private String extractText(Element parent, String selector, boolean textOnly) {
        if (selector != null && !selector.trim().isEmpty()) {
            Element el = parent.selectFirst(selector);
            if (el == null) return null;
            return textOnly ? el.text() : el.html();
        } else {
            return textOnly ? parent.text() : parent.html();
        }
    }
}
