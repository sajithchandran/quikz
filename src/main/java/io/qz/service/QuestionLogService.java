package io.qz.service;

import io.qz.domain.QuestionLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing QuestionLog.
 */
public interface QuestionLogService {

    /**
     * Save a questionLog.
     *
     * @param questionLog the entity to save
     * @return the persisted entity
     */
    QuestionLog save(QuestionLog questionLog);

    /**
     * Get all the questionLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionLog> findAll(Pageable pageable);


    /**
     * Get the "id" questionLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QuestionLog> findOne(Long id);

    /**
     * Delete the "id" questionLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the questionLog corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionLog> search(String query, Pageable pageable);
}
