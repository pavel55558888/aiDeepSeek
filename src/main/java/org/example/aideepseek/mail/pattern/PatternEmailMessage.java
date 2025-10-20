package org.example.aideepseek.mail.pattern;

public enum PatternEmailMessage {
    SUBJECT("Регистрация аккаунта MindAbyss AI"),
    BODY("Ваш код подтверждения: %s\nНеобходимо подтвердить вашу почту в течение 10 минут");

    private final String template;

    PatternEmailMessage(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        return String.format(template, args);
    }

    public String getTemplate() {
        return template;
    }
}
