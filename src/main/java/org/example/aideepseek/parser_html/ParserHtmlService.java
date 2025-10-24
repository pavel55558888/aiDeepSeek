package org.example.aideepseek.parser_html;

import org.example.aideepseek.parser_html.model.ParsingInstruction;
import org.example.aideepseek.parser_html.model.QuestionWithAnswers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParserHtmlService {
    public String removeCssAndJs(String htmlContent);
    public List<QuestionWithAnswers> parseQuestions(String html, ParsingInstruction instruction);

}
