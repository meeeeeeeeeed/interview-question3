package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * ReplyDTO class, holds information about reply and Id of associated question
 */
@Data
@Builder
public class ReplyDTO {

    private Long questionId;
    private Long id;
    @NotNull
    private String author;
    @NotNull
    private String message;
}
