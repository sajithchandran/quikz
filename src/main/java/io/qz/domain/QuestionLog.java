package io.qz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.qz.domain.enumeration.Answer;

import io.qz.domain.enumeration.QuestionType;

import io.qz.domain.enumeration.Difficulty;

import io.qz.domain.enumeration.Status;

/**
 * A QuestionLog.
 */
@Entity
@Table(name = "question_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "questionlog")
public class QuestionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "a")
    private String a;

    @Column(name = "b")
    private String b;

    @Column(name = "c")
    private String c;

    @Column(name = "d")
    private String d;

    @Enumerated(EnumType.STRING)
    @Column(name = "answer")
    private Answer answer;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "send_for_approval_date")
    private Instant sendForApprovalDate;

    @Column(name = "approved_date")
    private Instant approvedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getA() {
        return a;
    }

    public QuestionLog a(String a) {
        this.a = a;
        return this;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public QuestionLog b(String b) {
        this.b = b;
        return this;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public QuestionLog c(String c) {
        this.c = c;
        return this;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public QuestionLog d(String d) {
        this.d = d;
        return this;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Answer getAnswer() {
        return answer;
    }

    public QuestionLog answer(Answer answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public QuestionLog createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public QuestionLog updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getSendForApprovalDate() {
        return sendForApprovalDate;
    }

    public QuestionLog sendForApprovalDate(Instant sendForApprovalDate) {
        this.sendForApprovalDate = sendForApprovalDate;
        return this;
    }

    public void setSendForApprovalDate(Instant sendForApprovalDate) {
        this.sendForApprovalDate = sendForApprovalDate;
    }

    public Instant getApprovedDate() {
        return approvedDate;
    }

    public QuestionLog approvedDate(Instant approvedDate) {
        this.approvedDate = approvedDate;
        return this;
    }

    public void setApprovedDate(Instant approvedDate) {
        this.approvedDate = approvedDate;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public QuestionLog questionType(QuestionType questionType) {
        this.questionType = questionType;
        return this;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public QuestionLog difficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Status getStatus() {
        return status;
    }

    public QuestionLog status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Question getQuestion() {
        return question;
    }

    public QuestionLog question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionLog questionLog = (QuestionLog) o;
        if (questionLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionLog{" +
            "id=" + getId() +
            ", a='" + getA() + "'" +
            ", b='" + getB() + "'" +
            ", c='" + getC() + "'" +
            ", d='" + getD() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", sendForApprovalDate='" + getSendForApprovalDate() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            ", questionType='" + getQuestionType() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
