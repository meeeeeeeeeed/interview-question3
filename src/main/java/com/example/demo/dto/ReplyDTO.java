package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ReplyDTO class, holds information about reply and Id of associated question
 */
@Getter
@Builder
public class ReplyDTO {

    private final Long questionId;
    private final Long id;
    @NotNull
    @NotBlank
    private final String author;
    @NotNull
    @NotBlank
    private final String message;
}
