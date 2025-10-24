package org.example.aideepseek.parser_html.model;

public class ParsingInstruction {
    // CSS-селектор для контейнера каждого вопроса (например, ".question-block")
    private String questionContainerSelector;

    // CSS-селектор для текста вопроса внутри контейнера (например, ".question-text")
    private String questionTextSelector;

    // CSS-селектор для каждого ответа внутри контейнера (например, ".answer-option")
    private String answerSelector;

    // Опционально: нужно ли извлекать только текст (true по умолчанию)
    private boolean textOnly = true;

    // Геттеры и сеттеры
    public String getQuestionContainerSelector() { return questionContainerSelector; }
    public void setQuestionContainerSelector(String questionContainerSelector) { this.questionContainerSelector = questionContainerSelector; }

    public String getQuestionTextSelector() { return questionTextSelector; }
    public void setQuestionTextSelector(String questionTextSelector) { this.questionTextSelector = questionTextSelector; }

    public String getAnswerSelector() { return answerSelector; }
    public void setAnswerSelector(String answerSelector) { this.answerSelector = answerSelector; }

    public boolean isTextOnly() { return textOnly; }
    public void setTextOnly(boolean textOnly) { this.textOnly = textOnly; }

    @Override
    public String toString() {
        return "{" +
                "questionContainerSelector='" + questionContainerSelector + '\'' +
                ", questionTextSelector='" + questionTextSelector + '\'' +
                ", answerSelector='" + answerSelector + '\'' +
                ", textOnly=" + textOnly +
                '}';
    }
}
