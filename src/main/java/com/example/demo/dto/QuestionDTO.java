package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Question DTO class, holds information about question
 */
@Data
@Builder
public class QuestionDTO {

    private Long id;
    @NotNull
    private String author;
    @NotNull
    private String message;

    private int replies;
}

