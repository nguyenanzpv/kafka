package com.java.project2.service.impl;

import com.java.project2.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmailService emailService;

    //id=notificationGroup de chung se chung 1 group
    @KafkaListener(id="notificationGroup",topics="notification")
    public void listen(MessageDTO messageDTO){
        logger.info("Receive:...",messageDTO.getTo());
        emailService.sendMail(messageDTO);

    }
}
