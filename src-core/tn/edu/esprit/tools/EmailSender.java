package tn.edu.esprit.tools;

public interface EmailSender {
    void send(String toEmail, String subject, String body);
}
