package com.taskstrategy.business.service;

import com.taskstrategy.business.api.EmailService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class EmailServiceImpl implements EmailService{

    @Override
    public boolean send(String emailAddress, String subject, String body) {
        try
        {
            send("localhost", emailAddress, "taskstrategy@taskstrategy.com", subject, body);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * "send" method to send the email.
     * Source: http://www.javaworld.com/article/2075785/java-se/javamail-quick-start.html
     */

    private static void send(String smtpServer, String to, String from
            , String subject, String body)
    {
        try
        {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpServer);

            Session session = Session.getDefaultInstance(props, null);

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setSentDate(new Date());

            Transport.send(msg);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
