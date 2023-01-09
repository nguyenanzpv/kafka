package com.java.project2.service.impl;

import com.java.project2.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    //trung voi mail cau hinh trong application.properties
    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendMail(MessageDTO messageDTO){
        try{
            logger.info(("Starting... sending email"));
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
            //load template email with content
            Context context = new Context();
            context.setVariable("name", messageDTO.getToName());
            context.setVariable("content", messageDTO.getContent());
            String html= templateEngine.process("welcome-email",context);
            //send mail
            helper.setTo(messageDTO.getTo());
            helper.setFrom(from);
            helper.setSubject(messageDTO.getSubject());
            helper.setText(html,true);
            javaMailSender.send(message);
            logger.info("End.... email success");
        }catch(MessagingException e){
            logger.error("mail failed with error: "+e.getMessage());
        }
    }

}
