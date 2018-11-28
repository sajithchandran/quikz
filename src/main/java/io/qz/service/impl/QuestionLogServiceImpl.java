package io.qz.service.impl;

import io.qz.service.QuestionLogService;
import io.qz.domain.QuestionLog;
import io.qz.repository.QuestionLogRepository;
import io.qz.repository.search.QuestionLogSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QuestionLog.
 */
@Service
@Transactional
public class QuestionLogServiceImpl implements QuestionLogService {

    private final Logger log = LoggerFactory.getLogger(QuestionLogServiceImpl.class);

    private final QuestionLogRepository questionLogRepository;

    private final QuestionLogSearchRepository questionLogSearchRepository;

    public QuestionLogServiceImpl(QuestionLogRepository questionLogRepository, QuestionLogSearchRepository questionLogSearchRepository) {
        this.questionLogRepository = questionLogRepository;
        this.questionLogSearchRepository = questionLogSearchRepository;
    }

    /**
     * Save a questionLog.
     *
     * @param questionLog the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionLog save(QuestionLog questionLog) {
        log.debug("Request to save QuestionLog : {}", questionLog);
        QuestionLog result = questionLogRepository.save(questionLog);
        questionLogSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the questionLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionLog> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionLogs");
        return questionLogRepository.findAll(pageable);
    }


    /**
     * Get one questionLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionLog> findOne(Long id) {
        log.debug("Request to get QuestionLog : {}", id);
        return questionLogRepository.findById(id);
    }

    /**
     * Delete the questionLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionLog : {}", id);
        questionLogRepository.deleteById(id);
        questionLogSearchRepository.deleteById(id);
    }

    /**
     * Search for the questionLog corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionLog> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QuestionLogs for query {}", query);
        return questionLogSearchRepository.search(queryStringQuery(query), pageable);    }
}
