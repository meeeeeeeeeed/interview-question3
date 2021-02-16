package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * ThreadReplyDTO class, holds information about reply. It is not pointing to related question.
 */
@Getter
@Builder
public class ThreadReplyDTO {

    private final Long id;
    @NotNull
    private final String author;
    @NotNull
    private final String message;
}
