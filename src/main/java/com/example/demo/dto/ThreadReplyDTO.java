package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * ThreadReplyDTO class, holds information about reply. It is not pointing to related question.
 */
@Getter
@Builder
public class ThreadReplyDTO {

    private Long id;
    @NotNull
    private String author;
    @NotNull
    private String message;
}
