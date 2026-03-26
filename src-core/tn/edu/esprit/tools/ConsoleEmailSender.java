package tn.edu.esprit.tools;

public class ConsoleEmailSender implements EmailSender {

    @Override
    public void send(String toEmail, String subject, String body) {
        throw new IllegalStateException("SMTP environment variables are missing.");
    }
}
