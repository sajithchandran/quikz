package io.qz.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of QuestionLogSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class QuestionLogSearchRepositoryMockConfiguration {

    @MockBean
    private QuestionLogSearchRepository mockQuestionLogSearchRepository;

}
