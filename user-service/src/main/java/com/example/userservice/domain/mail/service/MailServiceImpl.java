package com.example.userservice.domain.mail.service;

import com.example.userservice.domain.mail.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailServiceImpl implements MailService{


    private final JavaMailSender mailSender;
    private MimeMessage mimeMailMessage;
    private MimeMessageHelper messageHelper;

    @Value("${spring.mail.username}")
    private String mailSenderEmail;


    public MailServiceImpl(JavaMailSender mailSender) throws MessagingException {
        this.mailSender = mailSender;
        mimeMailMessage = this.mailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
    }


    private void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    private void setText(String htmlContent) throws MessagingException {
        messageHelper.setText(htmlContent, true);
    }

    private void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
        messageHelper.setFrom(email, name);
    }

    private void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    private void addInline(String contentId, DataSource dataSource) throws MessagingException {
        messageHelper.addInline(contentId, dataSource);
    }

    private void send() {
        mailSender.send(mimeMailMessage);
    }



    @Override
    public void sendMail(Email email) throws MessagingException, UnsupportedEncodingException {

        String authKey = email.getAuthKey();
        setSubject("[꺅 인증해주세여 ><]");

        setText(makeMailMessage(email));

        setFrom("a46151681@gmail.com", "신동훈");
        setTo(email.getEmail());
        send();
    }

    private String makeMailMessage(Email email) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>메일인증</h1><br/>아래 [이메일 인증 확인]을 눌러주세요.")
                .append("<a href='http://localhost:8080/member/registerEmail?emailId=")
                .append(email.getId())
                .append("&key=")
                .append(email.getAuthKey())
                        .append("' target='_blenk'>이메일 인증 확인</a>");
        return sb.toString();
    }
}
