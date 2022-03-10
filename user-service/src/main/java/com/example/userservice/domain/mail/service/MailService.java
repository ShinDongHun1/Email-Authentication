package com.example.userservice.domain.mail.service;

import com.example.userservice.domain.mail.Email;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {

    void sendMail(Email email) throws MessagingException, UnsupportedEncodingException;

}
