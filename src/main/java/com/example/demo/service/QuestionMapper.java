package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.dto.ThreadReplyDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * QuestionMapper class, map entities and DTOs in both directions.
 */
@Component
public class QuestionMapper {

    /**
     * Map Question entity object to QuestionDTO
     *
     * @param question is an entity object
     * @return QuestionDTO
     */
    QuestionDTO questionToQuestionDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .author(question.getAuthor())
                .message(question.getMessage())
                .replies(question.getReplies() == null ? 0L : question.getReplies().size())
                .build();
    }

    /**
     * Map QuestionDTO to Question entity object
     *
     * @param questionDTO is an entity object
     * @return QuestionDTO
     */
    Question questionDTOToQuestion(QuestionDTO questionDTO) {
        return Question.builder()
                .author(questionDTO.getAuthor())
                .message(questionDTO.getMessage())
                .build();
    }

    /**
     * Map question entity to ThreadDTO
     *
     * @param question is an entity object
     * @return ThreadDTO with is transfer object for Question and related Replies
     */
    ThreadDTO questionToThreadDTO(Question question) {
        return ThreadDTO.builder()
                .id(question.getId())
                .message(question.getMessage())
                .replies(replyListToThreadReplyDTOList(question.getReplies()))
                .author(question.getAuthor())
                .build();
    }

    /**
     * Map replies entity to ThreadRepliesDTO list
     *
     * @param replies list of replies entities
     * @return List of ThreadReplyDTO, hold reply list without relation to question
     */
    private List<ThreadReplyDTO> replyListToThreadReplyDTOList(List<Reply> replies) {
        return Optional.ofNullable(replies)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::replyToThreadReplyDTO)
                .collect(Collectors.toList());
    }

    /**
     * Map single reply entity to ThreadRepliesDTO
     *
     * @param reply entity object
     * @return ThreadReplyDTO, hold reply without relation to question
     */
    ThreadReplyDTO replyToThreadReplyDTO(Reply reply) {
        return ThreadReplyDTO.builder()
                .id(reply.getId())
                .message(reply.getMessage())
                .author(reply.getAuthor())
                .build();
    }

    /**
     * Map Reply entity to ReplyDTO
     *
     * @param reply entity object
     * @return ReplyDTO
     */
    ReplyDTO replyToReplyDTO(Reply reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .questionId(reply.getQuestion().getId())
                .message(reply.getMessage())
                .author(reply.getAuthor())
                .build();
    }

    /**
     * Map ReplyDTO to Reply entity
     *
     * @param replyDTO transfer object
     * @return Reply entity object
     */
    Reply replyDTOToReply(ReplyDTO replyDTO) {
        return Reply.builder()
                .message(replyDTO.getMessage())
                .author(replyDTO.getAuthor())
                .build();
    }
}
