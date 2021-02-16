package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Question DTO class, holds information about question
 */
@Getter
@Builder
public class QuestionDTO {

    private final Long id;
    @NotNull
    @NotBlank
    private final String author;
    @NotNull
    @NotBlank
    private final String message;

    private final Long replies;
}

