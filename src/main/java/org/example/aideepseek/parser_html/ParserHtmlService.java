package org.example.aideepseek.parser_html;

import org.example.aideepseek.dto.ParsingInstruction;
import org.example.aideepseek.dto.QuestionWithAnswers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParserHtmlService {
    public String removeCssAndJs(String htmlContent);
    public List<QuestionWithAnswers> parseQuestions(String html, ParsingInstruction instruction);

}
