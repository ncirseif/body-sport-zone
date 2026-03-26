package tn.edu.esprit.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class GmailSmtpEmailSender implements EmailSender {

    private final String username;
    private final String appPassword;

    public GmailSmtpEmailSender(String username, String appPassword) {
        this.username = username;
        this.appPassword = appPassword;
    }

    @Override
    public void send(String toEmail, String subject, String body) {
        String to = toEmail == null ? "" : toEmail.trim();
        if (to.isEmpty()) {
            throw new IllegalArgumentException("Recipient email is empty.");
        }

        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("smtp.gmail.com", 465);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            expect(reader, 220);
            send(writer, "EHLO localhost");
            expect(reader, 250);

            send(writer, "AUTH LOGIN");
            expect(reader, 334);

            send(writer, base64(username));
            expect(reader, 334);

            send(writer, base64(appPassword));
            expect(reader, 235);

            send(writer, "MAIL FROM:<" + username + ">");
            expect(reader, 250);

            send(writer, "RCPT TO:<" + to + ">");
            expect(reader, 250, 251);

            send(writer, "DATA");
            expect(reader, 354);

            writeDataLine(writer, "From: " + username);
            writeDataLine(writer, "To: " + to);
            writeDataLine(writer, "Subject: " + sanitize(subject));
            writeDataLine(writer, "MIME-Version: 1.0");
            writeDataLine(writer, "Content-Type: text/plain; charset=UTF-8");
            writeDataLine(writer, "");
            for (String line : sanitize(body).replace("\r", "").split("\n", -1)) {
                if (line.startsWith(".")) {
                    writeDataLine(writer, "." + line);
                } else {
                    writeDataLine(writer, line);
                }
            }
            writeDataLine(writer, ".");
            writer.flush();
            expect(reader, 250);

            send(writer, "QUIT");
            expect(reader, 221);
        } catch (Exception e) {
            throw new RuntimeException("Email send failed.", e);
        }
    }

    private static void send(BufferedWriter writer, String cmd) throws Exception {
        writer.write(cmd);
        writer.write("\r\n");
        writer.flush();
    }

    private static void writeDataLine(BufferedWriter writer, String line) throws Exception {
        writer.write(line);
        writer.write("\r\n");
    }

    private static String base64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String sanitize(String value) {
        return value == null ? "" : value.replace("\r", "");
    }

    private static void expect(BufferedReader reader, int... acceptedCodes) throws Exception {
        String line = reader.readLine();
        if (line == null || line.length() < 3) {
            throw new IllegalStateException("SMTP response is empty.");
        }

        while (line.length() > 3 && line.charAt(3) == '-') {
            String next = reader.readLine();
            if (next == null) {
                throw new IllegalStateException("SMTP multiline response interrupted.");
            }
            line = next;
        }

        int code = Integer.parseInt(line.substring(0, 3));
        for (int acceptedCode : acceptedCodes) {
            if (code == acceptedCode) {
                return;
            }
        }
        throw new IllegalStateException("SMTP unexpected response: " + line);
    }
}
