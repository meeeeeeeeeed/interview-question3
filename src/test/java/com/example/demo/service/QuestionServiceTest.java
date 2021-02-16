package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.ReplyRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuestionServiceTest {

    private static final Random random = new Random();

    QuestionRepository questionRepository;
    ReplyRepository replyRepository;
    QuestionService questionService;

    @BeforeEach
    public void before() {
        questionRepository = mock(QuestionRepository.class);
        replyRepository = mock(ReplyRepository.class);

        questionService = new QuestionService(questionRepository, replyRepository, new QuestionMapper());
    }

    /**
     * When execute getAllQuestions method and Questions repository is empty should return empty list.
     */
    @Test
    void shouldReturnEmptyListWhenNoQuestionsInRepository() {
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        List<QuestionDTO> allQuestions = questionService.getAllQuestions();

        assertTrue(allQuestions.isEmpty());
    }

    /**
     * When execute getAllQuestions method and Question repository is not empty should return list of QueriesDTO
     */
    @Test
    void shouldReturnAllQueriesFromRepositoryMappedToQueryDTO() {
        List<Question> questions = createQuestionListWithReply();
        when(questionRepository.findAll()).thenReturn(questions);

        List<QuestionDTO> allQuestions = questionService.getAllQuestions();

        assertFalse(allQuestions.isEmpty());
        assertEquals(questions.size(), allQuestions.size());
    }

    /**
     * When createQuestion method is called then service should invoke save method on QuestionRepository and return not null object
     */
    @Test
    void shouldInvokeSaveMethodOnQuestionsRepositoryAndReturnObjectWhenCreateMethodIsCalled() {
        QuestionDTO toCreate = QuestionDTO.builder().build();
        Question entity = createRandomQuestion(Collections.emptyList());
        when(questionRepository.save(any())).thenReturn(entity);

        QuestionDTO createdDTO = questionService.createQuestion(toCreate);

        verify(questionRepository, times(1)).save(any());
        assertNotNull(createdDTO);
    }

    /**
     * When createReplay method is invoked for not existing query in repository then QuestionNotFoundException should be thrown.
     */
    @Test
    void shouldThrowRuntimeExceptionWhenQuestionDoesntExistInRepository() {
        ReplyDTO replyDTO = ReplyDTO.builder().build();
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(QuestionNotFoundException.class, () ->
                questionService.createReply(replyDTO, 1L));
    }

    /**
     * When createReplay method is invoked for existing query
     * then replyRepository save() method is invoked once and not null object return.
     */
    @Test
    void shouldCreateReplyInRepositoryAndReturnNotNullObjectWhenQuestionExist() {
        long questionId = 1L;
        Question questionEntity = createRandomQuestion(Collections.emptyList());
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(questionEntity));
        when(replyRepository.save(any())).thenReturn(createRandomReply());

        ReplyDTO replyDTO = questionService.createReply(createRandomReplyDTO(), questionId);

        verify(replyRepository, times(1)).save(any());
        assertNotNull(replyDTO);
    }

    /**
     * When getThread method is invoked for existing question Id
     * then not null object should be returned with correct ID
     */
    @Test
    void ShouldReturnNotNullThreadDTOWhenQuestionExistInRepository() {
        Long questionId = 1L;
        Question questionEntity = createRandomQuestion(Collections.singletonList(createRandomReply()));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(questionEntity));

        ThreadDTO threadDTO = questionService.getThread(questionId);

        assertNotNull(threadDTO);
        assertEquals(questionEntity.getId(), threadDTO.getId());
    }

    /**
     * When getThread method is invoked for not existing Question in repository
     * then QuestionNotFoundException should be thrown.
     */
    @Test
    void shouldThrowRuntimeExceptionWhenQuestionDoesntExistInRepositoryForGivenThread() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(QuestionNotFoundException.class, () ->
                questionService.getThread(1L));
    }

    private List<Question> createQuestionListWithReply() {
        Reply reply = createRandomReply();
        Question question = createRandomQuestion(Collections.singletonList(reply));
        reply.setQuestion(question);
        return Collections.singletonList(question);
    }

    private Reply createRandomReply() {
        return Reply.builder()
                .author(RandomString.make())
                .message(RandomString.make())
                .id(random.nextLong())
                .question(Question.builder().build())
                .build();
    }

    private ReplyDTO createRandomReplyDTO() {
        return ReplyDTO.builder()
                .id(random.nextLong())
                .author(RandomString.make())
                .message(RandomString.make())
                .build();
    }

    private Question createRandomQuestion(List<Reply> replies) {
        return Question.builder()
                .id(random.nextLong())
                .message(RandomString.make())
                .author(RandomString.make())
                .replies(replies)
                .build();
    }

}