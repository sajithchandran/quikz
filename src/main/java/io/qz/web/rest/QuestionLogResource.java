package io.qz.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.qz.domain.QuestionLog;
import io.qz.service.QuestionLogService;
import io.qz.web.rest.errors.BadRequestAlertException;
import io.qz.web.rest.util.HeaderUtil;
import io.qz.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing QuestionLog.
 */
@RestController
@RequestMapping("/api")
public class QuestionLogResource {

    private final Logger log = LoggerFactory.getLogger(QuestionLogResource.class);

    private static final String ENTITY_NAME = "questionLog";

    private final QuestionLogService questionLogService;

    public QuestionLogResource(QuestionLogService questionLogService) {
        this.questionLogService = questionLogService;
    }

    /**
     * POST  /question-logs : Create a new questionLog.
     *
     * @param questionLog the questionLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionLog, or with status 400 (Bad Request) if the questionLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-logs")
    @Timed
    public ResponseEntity<QuestionLog> createQuestionLog(@RequestBody QuestionLog questionLog) throws URISyntaxException {
        log.debug("REST request to save QuestionLog : {}", questionLog);
        if (questionLog.getId() != null) {
            throw new BadRequestAlertException("A new questionLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionLog result = questionLogService.save(questionLog);
        return ResponseEntity.created(new URI("/api/question-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-logs : Updates an existing questionLog.
     *
     * @param questionLog the questionLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionLog,
     * or with status 400 (Bad Request) if the questionLog is not valid,
     * or with status 500 (Internal Server Error) if the questionLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-logs")
    @Timed
    public ResponseEntity<QuestionLog> updateQuestionLog(@RequestBody QuestionLog questionLog) throws URISyntaxException {
        log.debug("REST request to update QuestionLog : {}", questionLog);
        if (questionLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionLog result = questionLogService.save(questionLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-logs : get all the questionLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questionLogs in body
     */
    @GetMapping("/question-logs")
    @Timed
    public ResponseEntity<List<QuestionLog>> getAllQuestionLogs(Pageable pageable) {
        log.debug("REST request to get a page of QuestionLogs");
        Page<QuestionLog> page = questionLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/question-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /question-logs/:id : get the "id" questionLog.
     *
     * @param id the id of the questionLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionLog, or with status 404 (Not Found)
     */
    @GetMapping("/question-logs/{id}")
    @Timed
    public ResponseEntity<QuestionLog> getQuestionLog(@PathVariable Long id) {
        log.debug("REST request to get QuestionLog : {}", id);
        Optional<QuestionLog> questionLog = questionLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionLog);
    }

    /**
     * DELETE  /question-logs/:id : delete the "id" questionLog.
     *
     * @param id the id of the questionLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionLog(@PathVariable Long id) {
        log.debug("REST request to delete QuestionLog : {}", id);
        questionLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/question-logs?query=:query : search for the questionLog corresponding
     * to the query.
     *
     * @param query the query of the questionLog search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/question-logs")
    @Timed
    public ResponseEntity<List<QuestionLog>> searchQuestionLogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of QuestionLogs for query {}", query);
        Page<QuestionLog> page = questionLogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/question-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
