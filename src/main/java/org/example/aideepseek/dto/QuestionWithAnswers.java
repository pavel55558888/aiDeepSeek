package org.example.aideepseek.dto;

import java.util.List;

public class QuestionWithAnswers {
    private String question;
    private List<String> answers;

    // Конструкторы, геттеры, сеттеры
    public QuestionWithAnswers() {}

    public QuestionWithAnswers(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public List<String> getAnswers() { return answers; }
    public void setAnswers(List<String> answers) { this.answers = answers; }

    @Override
    public String toString() {
        return "QuestionWithAnswers{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
