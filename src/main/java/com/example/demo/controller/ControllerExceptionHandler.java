package com.example.demo.controller;

import com.example.demo.exception.QuestionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Custom controller exception handler class.
 * logs error messages and map Exceptions to correct ResponseStatuses
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * Wrap {@link QuestionNotFoundException} class exception to 404 HTTP Status - NOT_FOUND
     *
     * @param ex QuestionNotFoundException
     * @return responseEntity
     */
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<String> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Wrap {@link org.springframework.web.bind.MethodArgumentNotValidException} class exception to 400 HTTP Status - BAD_REQUEST
     *
     * @param ex MethodArgumentNotValidException
     * @return responseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Wrap {@link Exception} class exception to 500 HTTP Status - INTERNAL_SERVER_ERROR
     *
     * @param ex QuestionNotFoundException
     * @return responseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
