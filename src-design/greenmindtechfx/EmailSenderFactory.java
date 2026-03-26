package greenmindtechfx;

import tn.edu.esprit.tools.ConsoleEmailSender;
import tn.edu.esprit.tools.EmailSender;
import tn.edu.esprit.tools.GmailSmtpEmailSender;

public final class EmailSenderFactory {

    private EmailSenderFactory() {
    }

    public static EmailSender createFromEnv() {
        String gmailUser = System.getenv("GMAIL_SMTP_USER");
        String gmailAppPassword = System.getenv("GMAIL_SMTP_APP_PASSWORD");
        if (gmailUser == null || gmailAppPassword == null) {
            return new ConsoleEmailSender();
        }
        gmailUser = gmailUser.trim();
        gmailAppPassword = gmailAppPassword.trim();
        if (gmailUser.isEmpty() || gmailAppPassword.isEmpty()) {
            return new ConsoleEmailSender();
        }

        return new GmailSmtpEmailSender(gmailUser, gmailAppPassword);
    }
}
