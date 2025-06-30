package utilities;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String toEmail, String subject, String body) throws FileNotFoundException, IOException {
    	
//		Properties prop = new Properties();
//		prop.load(new FileInputStream("config.properties"));
//		String appPassword = prop.getProperty("password");
		
        final String fromEmail = "khyatichauhanofficial@gmail.com";
        final String password = "mqpr mhza ptbg jxjv";

        // SMTP server config
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        // Create a session with an authenticator
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Cart Ledger Report"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Set plain text body
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

