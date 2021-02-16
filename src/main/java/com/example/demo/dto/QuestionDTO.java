package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * Question DTO class, holds information about question
 */
@Getter
@Builder
public class QuestionDTO {

    private final Long id;
    @NotNull
    private final String author;
    @NotNull
    private final String message;

    private final int replies;
}

