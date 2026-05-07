package tn.edu.esprit.tools;

public class ConsoleEmailSender implements EmailSender {
    @Override
    public void send(String to, String subject, String body) {
        System.out.println("=== EMAIL (console fallback) ===");
        System.out.println("To:      " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:    " + body);
        System.out.println("================================");
    }
}
