package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Rest controller responsible for exposing Question resource at /questions
 */
@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Http POST request, allows to create new Question.
     *
     * @param questionDTO require author and message.
     * @return QuestionDTO with database ID.
     */
    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO createQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
        return questionService.createQuestion(questionDTO);
    }

    /**
     * Http POST request, allows to create new Replay for given Question.
     *
     * @param replyDTO   require author and message.
     * @param questionId id of Question
     * @return ReplayDTO with related database ID and Question ID.
     */
    @PostMapping("/questions/{questionId}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public ReplyDTO createReply(@RequestBody @Valid ReplyDTO replyDTO, @PathVariable("questionId") @NotNull Long questionId) {
        return questionService.createReply(replyDTO, questionId);
    }

    /**
     * Http GET request, as a path parameter takes Question Id and returns Question with all related Replies.
     *
     * @param questionId id of Question.
     * @return ThreadDTO with represent Question and related Replies.
     */
    @GetMapping("/questions/{questionId}")
    public ThreadDTO getThread(@PathVariable("questionId") Long questionId) {
        return questionService.getThread(questionId);
    }

    /**
     * Http GET request, returns all existing questions
     *
     * @return a list of all existing Questions
     */
    @GetMapping("/questions")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

}
