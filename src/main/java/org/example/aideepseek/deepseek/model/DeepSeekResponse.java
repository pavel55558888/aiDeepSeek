package org.example.aideepseek.deepseek.model;

import java.util.List;

public class DeepSeekResponse {
    private List<Choice> choices;

    public static class Choice {
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
