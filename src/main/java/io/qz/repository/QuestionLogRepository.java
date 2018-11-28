package io.qz.repository;

import io.qz.domain.QuestionLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionLogRepository extends JpaRepository<QuestionLog, Long> {

}
