package io.qz.web.rest;

import io.qz.QuikzApp;

import io.qz.domain.QuestionLog;
import io.qz.repository.QuestionLogRepository;
import io.qz.repository.search.QuestionLogSearchRepository;
import io.qz.service.QuestionLogService;
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
 * Test class for the QuestionLogResource REST controller.
 *
 * @see QuestionLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuikzApp.class)
public class QuestionLogResourceIntTest {

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
    private QuestionLogRepository questionLogRepository;

    @Autowired
    private QuestionLogService questionLogService;

    /**
     * This repository is mocked in the io.qz.repository.search test package.
     *
     * @see io.qz.repository.search.QuestionLogSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuestionLogSearchRepository mockQuestionLogSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionLogMockMvc;

    private QuestionLog questionLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionLogResource questionLogResource = new QuestionLogResource(questionLogService);
        this.restQuestionLogMockMvc = MockMvcBuilders.standaloneSetup(questionLogResource)
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
    public static QuestionLog createEntity(EntityManager em) {
        QuestionLog questionLog = new QuestionLog()
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
        return questionLog;
    }

    @Before
    public void initTest() {
        questionLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionLog() throws Exception {
        int databaseSizeBeforeCreate = questionLogRepository.findAll().size();

        // Create the QuestionLog
        restQuestionLogMockMvc.perform(post("/api/question-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionLog)))
            .andExpect(status().isCreated());

        // Validate the QuestionLog in the database
        List<QuestionLog> questionLogList = questionLogRepository.findAll();
        assertThat(questionLogList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionLog testQuestionLog = questionLogList.get(questionLogList.size() - 1);
        assertThat(testQuestionLog.getA()).isEqualTo(DEFAULT_A);
        assertThat(testQuestionLog.getB()).isEqualTo(DEFAULT_B);
        assertThat(testQuestionLog.getC()).isEqualTo(DEFAULT_C);
        assertThat(testQuestionLog.getD()).isEqualTo(DEFAULT_D);
        assertThat(testQuestionLog.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testQuestionLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testQuestionLog.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testQuestionLog.getSendForApprovalDate()).isEqualTo(DEFAULT_SEND_FOR_APPROVAL_DATE);
        assertThat(testQuestionLog.getApprovedDate()).isEqualTo(DEFAULT_APPROVED_DATE);
        assertThat(testQuestionLog.getQuestionType()).isEqualTo(DEFAULT_QUESTION_TYPE);
        assertThat(testQuestionLog.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testQuestionLog.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the QuestionLog in Elasticsearch
        verify(mockQuestionLogSearchRepository, times(1)).save(testQuestionLog);
    }

    @Test
    @Transactional
    public void createQuestionLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionLogRepository.findAll().size();

        // Create the QuestionLog with an existing ID
        questionLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionLogMockMvc.perform(post("/api/question-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionLog)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionLog in the database
        List<QuestionLog> questionLogList = questionLogRepository.findAll();
        assertThat(questionLogList).hasSize(databaseSizeBeforeCreate);

        // Validate the QuestionLog in Elasticsearch
        verify(mockQuestionLogSearchRepository, times(0)).save(questionLog);
    }

    @Test
    @Transactional
    public void getAllQuestionLogs() throws Exception {
        // Initialize the database
        questionLogRepository.saveAndFlush(questionLog);

        // Get all the questionLogList
        restQuestionLogMockMvc.perform(get("/api/question-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionLog.getId().intValue())))
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
    public void getQuestionLog() throws Exception {
        // Initialize the database
        questionLogRepository.saveAndFlush(questionLog);

        // Get the questionLog
        restQuestionLogMockMvc.perform(get("/api/question-logs/{id}", questionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionLog.getId().intValue()))
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
    public void getNonExistingQuestionLog() throws Exception {
        // Get the questionLog
        restQuestionLogMockMvc.perform(get("/api/question-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionLog() throws Exception {
        // Initialize the database
        questionLogService.save(questionLog);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockQuestionLogSearchRepository);

        int databaseSizeBeforeUpdate = questionLogRepository.findAll().size();

        // Update the questionLog
        QuestionLog updatedQuestionLog = questionLogRepository.findById(questionLog.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionLog are not directly saved in db
        em.detach(updatedQuestionLog);
        updatedQuestionLog
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

        restQuestionLogMockMvc.perform(put("/api/question-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionLog)))
            .andExpect(status().isOk());

        // Validate the QuestionLog in the database
        List<QuestionLog> questionLogList = questionLogRepository.findAll();
        assertThat(questionLogList).hasSize(databaseSizeBeforeUpdate);
        QuestionLog testQuestionLog = questionLogList.get(questionLogList.size() - 1);
        assertThat(testQuestionLog.getA()).isEqualTo(UPDATED_A);
        assertThat(testQuestionLog.getB()).isEqualTo(UPDATED_B);
        assertThat(testQuestionLog.getC()).isEqualTo(UPDATED_C);
        assertThat(testQuestionLog.getD()).isEqualTo(UPDATED_D);
        assertThat(testQuestionLog.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testQuestionLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQuestionLog.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testQuestionLog.getSendForApprovalDate()).isEqualTo(UPDATED_SEND_FOR_APPROVAL_DATE);
        assertThat(testQuestionLog.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);
        assertThat(testQuestionLog.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testQuestionLog.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testQuestionLog.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the QuestionLog in Elasticsearch
        verify(mockQuestionLogSearchRepository, times(1)).save(testQuestionLog);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionLog() throws Exception {
        int databaseSizeBeforeUpdate = questionLogRepository.findAll().size();

        // Create the QuestionLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionLogMockMvc.perform(put("/api/question-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionLog)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionLog in the database
        List<QuestionLog> questionLogList = questionLogRepository.findAll();
        assertThat(questionLogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the QuestionLog in Elasticsearch
        verify(mockQuestionLogSearchRepository, times(0)).save(questionLog);
    }

    @Test
    @Transactional
    public void deleteQuestionLog() throws Exception {
        // Initialize the database
        questionLogService.save(questionLog);

        int databaseSizeBeforeDelete = questionLogRepository.findAll().size();

        // Get the questionLog
        restQuestionLogMockMvc.perform(delete("/api/question-logs/{id}", questionLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionLog> questionLogList = questionLogRepository.findAll();
        assertThat(questionLogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the QuestionLog in Elasticsearch
        verify(mockQuestionLogSearchRepository, times(1)).deleteById(questionLog.getId());
    }

    @Test
    @Transactional
    public void searchQuestionLog() throws Exception {
        // Initialize the database
        questionLogService.save(questionLog);
        when(mockQuestionLogSearchRepository.search(queryStringQuery("id:" + questionLog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(questionLog), PageRequest.of(0, 1), 1));
        // Search the questionLog
        restQuestionLogMockMvc.perform(get("/api/_search/question-logs?query=id:" + questionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionLog.getId().intValue())))
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
        TestUtil.equalsVerifier(QuestionLog.class);
        QuestionLog questionLog1 = new QuestionLog();
        questionLog1.setId(1L);
        QuestionLog questionLog2 = new QuestionLog();
        questionLog2.setId(questionLog1.getId());
        assertThat(questionLog1).isEqualTo(questionLog2);
        questionLog2.setId(2L);
        assertThat(questionLog1).isNotEqualTo(questionLog2);
        questionLog1.setId(null);
        assertThat(questionLog1).isNotEqualTo(questionLog2);
    }
}
