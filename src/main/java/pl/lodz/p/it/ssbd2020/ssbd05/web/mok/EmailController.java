package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import com.sun.mail.smtp.SMTPTransport;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Properties;

public class EmailController {

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "ssbd202005@gmail.com";
    private static final String PASSWORD = "tzwsgrp22";

    private static final String EMAIL_SUBJECT = "Confirm your account";

    public static void sendMail(String mail, String token) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String link = request.getRequestURL()
                .substring(0, (request.getRequestURL().length() - request.getServletPath().length())).concat("/confirmAccount.xhtml?token=");
        String body = "<a href=\"" + link + token + "\">Click here to confirm your account</a>";

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", SMTP_SERVER);
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(body, "UTF-8", "html");
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPart);

            msg.setFrom(new InternetAddress(USERNAME));
            msg.setContent(mimeMultipart);
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail, false));
            msg.setSubject(EMAIL_SUBJECT);
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

