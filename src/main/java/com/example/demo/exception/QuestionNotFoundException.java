package com.example.demo.exception;

/**
 * Runtime exception thrown when Resource cannot be found.
 */
public class QuestionNotFoundException extends RuntimeException {

    public static final String ERROR_MESSAGE_FORMAT = "Question with id:%d does not exist.";

    public QuestionNotFoundException(Long questionId) {
        super(String.format(ERROR_MESSAGE_FORMAT, questionId));
    }
}
