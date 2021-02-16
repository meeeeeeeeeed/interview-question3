package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.dto.ThreadReplyDTO;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for @link. Do not bring full Spring context. Tests narrowed only to web layer.
 */
@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    /**
     * Helper method convert Object into json string
     *
     * @param obj Object to be converted to string
     * @return Json string representation of object
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * test for GET /guestions
     * When there is no Questions in repository then endpoint should return empty array and 200 OK HTTP status
     *
     * @throws Exception
     */
    @Test
    void shouldReturn200OkAndEmptyArrayOfQuestions_whenNoQuestionHasBeenCreated() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.EMPTY_LIST)));
    }

    /**
     * test for GET /guestions
     * When service throws exception then controller should return 500 HTTP status
     *
     * @throws Exception
     */
    @Test
    void shouldReturn500InternalServerError_whenServiceThrowsRuntimeError() throws Exception {
        when(questionService.getAllQuestions()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/questions"))
                .andExpect(status().isInternalServerError());
    }

    /**
     * test for GET /guestions
     * When there are Questions in repository then endpoint should return list of QuestionDTO and 200 OK HTTP status
     *
     * @throws Exception
     */
    @Test
    void shouldReturn200OkAndListOfQuestions_whenQuestionsExist() throws Exception {
        QuestionDTO question1 = QuestionDTO.builder().id(1L).replies(0L).author("author").message("message").build();
        QuestionDTO question2 = QuestionDTO.builder().id(1L).replies(0L).author("author").message("message").build();
        List<QuestionDTO> questionDTOs = Arrays.asList(question1, question2);

        when(questionService.getAllQuestions()).thenReturn(questionDTOs);

        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(questionDTOs)));
    }

    /**
     * test for POST /guestions
     * When Request body missing author or message then endpoint should return 400 BAD_REQUEST status
     *
     * @throws Exception
     */
    @Test
    void shouldReturn400ResponseCode_whenQuestionMissingAuthorOrMessage() throws Exception {
        QuestionDTO question = QuestionDTO.builder().build();
        mockMvc.perform(
                post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(question)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * test for POST /guestions
     * When correct Question is send then endpoint should response with 201 HTTP status and Question with assigned id in body.
     *
     * @throws Exception
     */
    @Test
    void shouldReturn201ResponseCode_whenQuestionIsCreated() throws Exception {
        QuestionDTO question = QuestionDTO.builder().author("author").message("message").build();
        QuestionDTO expected = QuestionDTO.builder().id(1L).replies(0L).author("author").message("message").build();

        when(questionService.createQuestion(any())).thenReturn(expected);

        mockMvc.perform(
                post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(question)))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(expected)));
    }

    /**
     * test for POST /questions/{questionId}/reply
     * When Request Body missing author or message then endpoint should return 400 BAD_REQUEST status
     *
     * @throws Exception
     */
    @Test
    void shouldReturn400ResponseCode_whenRequestBodyIsInvalid() throws Exception {
        ReplyDTO reply = ReplyDTO.builder().build();

        mockMvc.perform(
                post("/questions/1/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reply)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * test for POST /questions/{questionId}/reply
     * When question with given id doesnt exist then endpoint should return 404 NOT_FOUND status
     *
     * @throws Exception
     */
    @Test
    void shouldReturn404ResponseCode_whenQuestionDoesntExist() throws Exception {
        ReplyDTO reply = ReplyDTO.builder().author("author").message("message").build();

        when(questionService.createReply(any(), any())).thenThrow(new QuestionNotFoundException(1L));

        mockMvc.perform(
                post("/questions/1/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reply)))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * test for POST /questions/{questionId}/reply
     * When request is correct and question exist then endpoint should return 201 CREATED status and Body with assigned id
     *
     * @throws Exception
     */
    @Test
    void shouldReturn201ResponseCode_whenRequestIsCorrectAndQuestionExist() throws Exception {
        ReplyDTO request = ReplyDTO.builder().author("author").message("message").build();
        ReplyDTO expected = ReplyDTO.builder().id(1L).questionId(1L).author("author").message("message").build();

        when(questionService.createReply(any(), any())).thenReturn(expected);

        mockMvc.perform(
                post("/questions/1/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(expected)));
    }


    /**
     * test for GET /questions/{questionId}
     * When question with given id doesnt exist then endpoint should return 404 NOT_FOUND status
     *
     * @throws Exception
     */
    @Test
    void shouldReturn404ResponseCode_whenThreadDoesntExist1() throws Exception {
        when(questionService.getThread(1L)).thenThrow(new QuestionNotFoundException(1L));

        mockMvc.perform(
                get("/questions/1"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * test for GET /questions/{questionId}
     * When thread for given question ID exist then endpoint should return 200 OK status and correct response body.
     *
     * @throws Exception
     */
    @Test
    void shouldReturn200ResponseCode_whenThreadForGivenIdExist() throws Exception {
        ThreadDTO expected = ThreadDTO.builder().author("author").message("question").id(1L).replies(
                Collections.singletonList(ThreadReplyDTO.builder().author("author1").message("answer").id(2L).build())
        ).build();

        when(questionService.getThread(1L)).thenReturn(expected);

        mockMvc.perform(
                get("/questions/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(expected)));
    }


}