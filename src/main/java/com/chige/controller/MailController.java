package com.chige.controller;

import com.chige.handler.MailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {

    @Autowired
    private MailHandler mailHandler;
    @GetMapping("/async")
    public String async(){
        try {
            mailHandler.sendMailRandom();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
