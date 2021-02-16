package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ThreadReplyDTO {

    private Long id;
    @NotNull
    private String author;
    @NotNull
    private String message;
}
