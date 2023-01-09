package com.java.project2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;

@ControllerAdvice(basePackages = "com.java.project2.controller")
public class ExceptionController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    // Bat cac loai exception tai day
    @ExceptionHandler(NoResultException.class)
    public String noResult(NoResultException ex){
        logger.error("ex: ",ex);
        return "404.html";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception ex){
        logger.error("sql ex: ",ex);
        return "exception.html";
    }
}
