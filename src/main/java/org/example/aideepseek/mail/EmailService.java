package org.example.aideepseek.mail;

public interface EmailService {
    public boolean sendEmail(String to, String subject, String body);
}
