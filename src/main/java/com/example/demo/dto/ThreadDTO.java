package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ThreadDTO {

    private Long id;
    @NotNull
    private String author;
    @NotNull
    private String message;

    private List<ThreadReplyDTO> replies;
}
