package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ThreadDTO class, holds information about question and associated replies.
 */
@Getter
@Builder
public class ThreadDTO {

    private final Long id;
    @NotNull
    private final String author;
    @NotNull
    private final String message;

    private final List<ThreadReplyDTO> replies;
}
