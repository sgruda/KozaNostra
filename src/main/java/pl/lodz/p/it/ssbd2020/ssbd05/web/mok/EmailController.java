package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailController {

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "ssbd202005@gmail.com";
    private static final String PASSWORD = "tzwsgrp22";

    private static final String EMAIL_SUBJECT = "Confirm your account";
    private static final String EMAIL_TEXT = "Click the link below to confirm your account.";

    private static final String LINK = "https://localhost:8181/ssbd05/confirmAccount.xhtml?token=";
//    private static final String LINK = "https://studapp.it.p.lodz.pl:8405/ssbd05/confirmAccount.xhtml?token=";

    public static void sendMail(String mail, String token) {

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", SMTP_SERVER);
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(USERNAME));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail, false));
            msg.setSubject(EMAIL_SUBJECT);
            msg.setText("<html><body><a href=" + LINK + token + ">Click here to confirm your account</a></body></html>");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

