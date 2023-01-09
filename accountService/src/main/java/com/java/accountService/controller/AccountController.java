package com.java.accountService.controller;

import com.java.accountService.model.AccountDTO;
import com.java.accountService.model.MessageDTO;
import com.java.accountService.model.StatisticDTO;
import com.java.accountService.service.AccountService;
import com.java.accountService.service.impl.MyProducerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {
    @Autowired
    KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    private AccountService accountService;

    //add new
    @PostMapping("/account")
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO){
        StatisticDTO start = new StatisticDTO("Account "+accountDTO.getEmail()+"is created",new Date());
        //send notification
        MessageDTO message = new MessageDTO();
        message.setTo(accountDTO.getEmail());
        message.setToName(accountDTO.getName());
        message.setSubject("Welcome to Kafka");
        message.setContent("Kafka with spring boot");

        //demo kafka send event bi lost data
        ListenableFuture<SendResult<String, Object>> sendResultListenableFuture;

        for(int i=0;i<100;i++){
            sendResultListenableFuture = kafkaTemplate.send("notification",message);
            sendResultListenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

                @Override
                public void onSuccess(SendResult<String, Object> result) {
                    handleSuccess("notification",message,result);
                }

                @Override
                public void onFailure(Throwable ex) {

                    handleFailure("notification",message, ex);

                }
            });
        }



        kafkaTemplate.send("statistic",start);

        //accountService.add(accountDTO);
        return accountDTO;
    }

    private void handleSuccess(String key, Object value, SendResult<String, Object> result) {

        //log.info("The record with key : {}, value : {} is produced sucessfullly to offset {}", key, value, result.getRecordMetadata().offset());

    }

    private void handleFailure(String key, Object value, Throwable ex) {

        //log.info("The record with key: {}, value: {} cannot be processed! caused by {}", key, value, ex.getMessage());

    }

    //get all
    @GetMapping("/accounts")
    public List<AccountDTO> getAll(){
        return accountService.getAll();
    }

    //Optional tra ve dang if..else tim thay hay ko
    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> get(@PathVariable(name = "id") Long id){
        return Optional.of(new ResponseEntity<AccountDTO>(accountService.getOne(id), HttpStatus.OK))
                .orElse(new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable(name="id") Long id){
        accountService.delete(id);
    }

    @PutMapping("/account")
    public void update(@RequestBody AccountDTO accountDTO){
        accountService.update(accountDTO);
    }
}
