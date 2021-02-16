package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Question service class, encapsulate logical operations on Questions and Replies
 */
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ReplyRepository replyRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository, ReplyRepository replyRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.replyRepository = replyRepository;
        this.questionMapper = questionMapper;
    }

    /**
     * Method returns all existing Questions in repository
     *
     * @return List of QuestionDTO
     */
    public List<QuestionDTO> getAllQuestions() {
        return questionRepository
                .findAll()
                .stream()
                .map(questionMapper::questionToQuestionDTO)
                .collect(Collectors.toList());
    }

    /**
     * Method create a new Question in repository
     *
     * @param questionDTO transfer object
     * @return QuestionDTO
     */
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question question = questionRepository
                .save(questionMapper.questionDTOToQuestion(questionDTO));
        return questionMapper.questionToQuestionDTO(question);

    }

    /**
     * Method create a new Replay to given question
     *
     * @param replyDTO   transfer object
     * @param questionId id of related question
     * @return ReplyDTO
     */
    public ReplyDTO createReply(ReplyDTO replyDTO, Long questionId) {
        Question question = findQuestionById(questionId);

        Reply reply = questionMapper.replyDTOToReply(replyDTO);
        reply.setQuestion(question);
        return questionMapper.replyToReplyDTO(replyRepository.save(reply));
    }

    /**
     * Method returns Question with all related Replies wrapped into ThreadDTO object for given question ID
     *
     * @param questionId id of
     * @return ThreadDTO transfer object, holds Question with related Replies
     */
    public ThreadDTO getThread(Long questionId) {
        Question question = findQuestionById(questionId);
        return questionMapper.questionToThreadDTO(question);
    }

    /**
     * Methods try to find Question entity if not exist runtime exception {@link QuestionNotFoundException} is throw
     *
     * @param questionId id of question
     * @return Question entity object
     */
    private Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
    }
}
