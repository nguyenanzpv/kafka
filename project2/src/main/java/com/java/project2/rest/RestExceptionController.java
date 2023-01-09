package com.java.project2.rest;

import com.java.project2.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;

@RestController
@RestControllerAdvice(basePackages = "com.java.project2.rest")
public class RestExceptionController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //bat cac loai exception tai day
    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseDTO<Void> noResult(NoResultException ex){
        logger.error("ex: ",ex);
        return ResponseDTO.<Void>builder().status(404).error("Not Found").build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseDTO<Void> badInput(MethodArgumentNotValidException ex){
        //logger.error("sql ex: ",ex);
        String message = ex.getBindingResult().getAllErrors().stream().map((error)->{
            return ((FieldError) error).getField()+" "+error.getDefaultMessage();
        }).findAny().orElse("");
        return ResponseDTO.<Void>builder().status(400).error(message).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<Void> exception(Exception ex){
        logger.error("sql ex: ",ex);
        return ResponseDTO.<Void>builder().status(500).error("Internal Server Error").build();
    }
}
