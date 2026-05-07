package tn.edu.esprit.tools;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSmtpEmailSender implements EmailSender {

    private final String user;
    private final String appPassword;

    public GmailSmtpEmailSender(String user, String appPassword) {
        this.user = user;
        this.appPassword = appPassword;
    }

    @Override
    public void send(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            "smtp.gmail.com");
        props.put("mail.smtp.port",            "587");

        javax.mail.Session session = javax.mail.Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, appPassword);
                }
            });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user));
            msg.setRecipients(Message.RecipientType.TO,
                              InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
            System.out.println("Email sent to: " + to);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: "
                                       + e.getMessage(), e);
        }
    }
}