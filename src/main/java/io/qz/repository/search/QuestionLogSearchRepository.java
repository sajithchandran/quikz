package io.qz.repository.search;

import io.qz.domain.QuestionLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QuestionLog entity.
 */
public interface QuestionLogSearchRepository extends ElasticsearchRepository<QuestionLog, Long> {
}
