package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.Email;
import com.ms.email.repositories.IEmailRepositoryBasic;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.Optional;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    IEmailRepositoryBasic emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @PersistenceContext
    private EntityManager em;
    private Properties properties;
    private Session session;
    private Authenticator authenticator;

    @Value("${spring.mail.host}")
    private String smtpHost;
    @Value("${spring.mail.port}")
    private String smtpPort;
    @Value("${spring.mail.isEnableSsl}")
    private String isEnableSsl;
    @Value("${spring.mail.isEnableAuth}")
    private String isEnableAuth;
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${spring.mail.password}")
    private String password;
    private final String TYPE_TEXT = "text/html; charset=utf-8";

    public Optional<?> sendEmail(@NotNull Email email) {
        Optional<?> res = null;
        try{
            MimeMessage message = setMessage(email);
            Transport.send(message);
            // set message sended
            email.setStatusEmail(StatusEmail.SENT.name());
            email.setSendDateEmail(Instant.now());
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            res = emailRepository.save(em, email);
        }
        return res;
    }

    private Session setSession(){
        try {
            properties = setProps();
            authenticator = setAuthenticator();
            session = Session.getInstance( properties, authenticator);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    private Properties setProps(){
        properties = System.getProperties();
        properties.put("mail.smtp.host",  smtpHost );
        properties.put("mail.smtp.port", smtpPort );
        properties.put("mail.smtp.ssl.enable", isEnableSsl );
        properties.put("mail.smtp.auth", isEnableAuth );
        return properties;
    }

    private Authenticator setAuthenticator(){
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication( userName, password );
            }
        };
        return auth;
    }

    private MimeMessage setMessage(Email email){
        Session session = setSession();
        MimeMessage message = new MimeMessage(session);
        try {
            Address addFrom = new InternetAddress(email.getEmailFrom());
            Address addTo   = new InternetAddress(email.getEmailTo());
            message.setFrom(addFrom);
            message.addRecipient(Message.RecipientType.TO, addTo);
            message.setSubject(email.getSubject());
            message.setContent(email.getText(), TYPE_TEXT);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }



}