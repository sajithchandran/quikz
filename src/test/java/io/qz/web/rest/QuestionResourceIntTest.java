package io.qz.web.rest;

import io.qz.QuikzApp;

import io.qz.domain.Question;
import io.qz.repository.QuestionRepository;
import io.qz.repository.search.QuestionSearchRepository;
import io.qz.service.QuestionService;
import io.qz.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static io.qz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.qz.domain.enumeration.Answer;
import io.qz.domain.enumeration.QuestionType;
import io.qz.domain.enumeration.Difficulty;
import io.qz.domain.enumeration.Status;
/**
 * Test class for the QuestionResource REST controller.
 *
 * @see QuestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuikzApp.class)
public class QuestionResourceIntTest {

    private static final String DEFAULT_A = "AAAAAAAAAA";
    private static final String UPDATED_A = "BBBBBBBBBB";

    private static final String DEFAULT_B = "AAAAAAAAAA";
    private static final String UPDATED_B = "BBBBBBBBBB";

    private static final String DEFAULT_C = "AAAAAAAAAA";
    private static final String UPDATED_C = "BBBBBBBBBB";

    private static final String DEFAULT_D = "AAAAAAAAAA";
    private static final String UPDATED_D = "BBBBBBBBBB";

    private static final Answer DEFAULT_ANSWER = Answer.A;
    private static final Answer UPDATED_ANSWER = Answer.B;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SEND_FOR_APPROVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEND_FOR_APPROVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_APPROVED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final QuestionType DEFAULT_QUESTION_TYPE = QuestionType.TEXT;
    private static final QuestionType UPDATED_QUESTION_TYPE = QuestionType.PICTURE;

    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.VERYEASY;
    private static final Difficulty UPDATED_DIFFICULTY = Difficulty.EASY;

    private static final Status DEFAULT_STATUS = Status.NEW;
    private static final Status UPDATED_STATUS = Status.WAITNG_FOR_APPROVAL;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    /**
     * This repository is mocked in the io.qz.repository.search test package.
     *
     * @see io.qz.repository.search.QuestionSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuestionSearchRepository mockQuestionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionMockMvc;

    private Question question;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionResource questionResource = new QuestionResource(questionService);
        this.restQuestionMockMvc = MockMvcBuilders.standaloneSetup(questionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createEntity(EntityManager em) {
        Question question = new Question()
            .a(DEFAULT_A)
            .b(DEFAULT_B)
            .c(DEFAULT_C)
            .d(DEFAULT_D)
            .answer(DEFAULT_ANSWER)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .sendForApprovalDate(DEFAULT_SEND_FOR_APPROVAL_DATE)
            .approvedDate(DEFAULT_APPROVED_DATE)
            .questionType(DEFAULT_QUESTION_TYPE)
            .difficulty(DEFAULT_DIFFICULTY)
            .status(DEFAULT_STATUS);
        return question;
    }

    @Before
    public void initTest() {
        question = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(question)))
            .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getA()).isEqualTo(DEFAULT_A);
        assertThat(testQuestion.getB()).isEqualTo(DEFAULT_B);
        assertThat(testQuestion.getC()).isEqualTo(DEFAULT_C);
        assertThat(testQuestion.getD()).isEqualTo(DEFAULT_D);
        assertThat(testQuestion.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testQuestion.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testQuestion.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testQuestion.getSendForApprovalDate()).isEqualTo(DEFAULT_SEND_FOR_APPROVAL_DATE);
        assertThat(testQuestion.getApprovedDate()).isEqualTo(DEFAULT_APPROVED_DATE);
        assertThat(testQuestion.getQuestionType()).isEqualTo(DEFAULT_QUESTION_TYPE);
        assertThat(testQuestion.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testQuestion.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Question in Elasticsearch
        verify(mockQuestionSearchRepository, times(1)).save(testQuestion);
    }

    @Test
    @Transactional
    public void createQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question with an existing ID
        question.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(question)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Question in Elasticsearch
        verify(mockQuestionSearchRepository, times(0)).save(question);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].a").value(hasItem(DEFAULT_A.toString())))
            .andExpect(jsonPath("$.[*].b").value(hasItem(DEFAULT_B.toString())))
            .andExpect(jsonPath("$.[*].c").value(hasItem(DEFAULT_C.toString())))
            .andExpect(jsonPath("$.[*].d").value(hasItem(DEFAULT_D.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sendForApprovalDate").value(hasItem(DEFAULT_SEND_FOR_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(DEFAULT_APPROVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId().intValue()))
            .andExpect(jsonPath("$.a").value(DEFAULT_A.toString()))
            .andExpect(jsonPath("$.b").value(DEFAULT_B.toString()))
            .andExpect(jsonPath("$.c").value(DEFAULT_C.toString()))
            .andExpect(jsonPath("$.d").value(DEFAULT_D.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.sendForApprovalDate").value(DEFAULT_SEND_FOR_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.approvedDate").value(DEFAULT_APPROVED_DATE.toString()))
            .andExpect(jsonPath("$.questionType").value(DEFAULT_QUESTION_TYPE.toString()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestion() throws Exception {
        // Initialize the database
        questionService.save(question);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockQuestionSearchRepository);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).get();
        // Disconnect from session so that the updates on updatedQuestion are not directly saved in db
        em.detach(updatedQuestion);
        updatedQuestion
            .a(UPDATED_A)
            .b(UPDATED_B)
            .c(UPDATED_C)
            .d(UPDATED_D)
            .answer(UPDATED_ANSWER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .sendForApprovalDate(UPDATED_SEND_FOR_APPROVAL_DATE)
            .approvedDate(UPDATED_APPROVED_DATE)
            .questionType(UPDATED_QUESTION_TYPE)
            .difficulty(UPDATED_DIFFICULTY)
            .status(UPDATED_STATUS);

        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestion)))
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getA()).isEqualTo(UPDATED_A);
        assertThat(testQuestion.getB()).isEqualTo(UPDATED_B);
        assertThat(testQuestion.getC()).isEqualTo(UPDATED_C);
        assertThat(testQuestion.getD()).isEqualTo(UPDATED_D);
        assertThat(testQuestion.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testQuestion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQuestion.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testQuestion.getSendForApprovalDate()).isEqualTo(UPDATED_SEND_FOR_APPROVAL_DATE);
        assertThat(testQuestion.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);
        assertThat(testQuestion.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testQuestion.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testQuestion.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Question in Elasticsearch
        verify(mockQuestionSearchRepository, times(1)).save(testQuestion);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Create the Question

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(question)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Question in Elasticsearch
        verify(mockQuestionSearchRepository, times(0)).save(question);
    }

    @Test
    @Transactional
    public void deleteQuestion() throws Exception {
        // Initialize the database
        questionService.save(question);

        int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Get the question
        restQuestionMockMvc.perform(delete("/api/questions/{id}", question.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Question in Elasticsearch
        verify(mockQuestionSearchRepository, times(1)).deleteById(question.getId());
    }

    @Test
    @Transactional
    public void searchQuestion() throws Exception {
        // Initialize the database
        questionService.save(question);
        when(mockQuestionSearchRepository.search(queryStringQuery("id:" + question.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(question), PageRequest.of(0, 1), 1));
        // Search the question
        restQuestionMockMvc.perform(get("/api/_search/questions?query=id:" + question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].a").value(hasItem(DEFAULT_A)))
            .andExpect(jsonPath("$.[*].b").value(hasItem(DEFAULT_B)))
            .andExpect(jsonPath("$.[*].c").value(hasItem(DEFAULT_C)))
            .andExpect(jsonPath("$.[*].d").value(hasItem(DEFAULT_D)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sendForApprovalDate").value(hasItem(DEFAULT_SEND_FOR_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(DEFAULT_APPROVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Question.class);
        Question question1 = new Question();
        question1.setId(1L);
        Question question2 = new Question();
        question2.setId(question1.getId());
        assertThat(question1).isEqualTo(question2);
        question2.setId(2L);
        assertThat(question1).isNotEqualTo(question2);
        question1.setId(null);
        assertThat(question1).isNotEqualTo(question2);
    }
}
