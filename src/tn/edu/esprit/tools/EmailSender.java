package tn.edu.esprit.tools;

public interface EmailSender {
    void send(String to, String subject, String body);
}
