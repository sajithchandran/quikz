package io.qz.domain;

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
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "question")
public class Question implements Serializable {

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

    @OneToOne    @JoinColumn(unique = true)
    private User createdBy;

    @OneToOne    @JoinColumn(unique = true)
    private User updatedBy;

    @OneToOne    @JoinColumn(unique = true)
    private User approvedBy;

    @OneToOne    @JoinColumn(unique = true)
    private Country country;

    @OneToOne    @JoinColumn(unique = true)
    private Category category;

    @OneToOne    @JoinColumn(unique = true)
    private Topic topic;

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

    public Question a(String a) {
        this.a = a;
        return this;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public Question b(String b) {
        this.b = b;
        return this;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public Question c(String c) {
        this.c = c;
        return this;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public Question d(String d) {
        this.d = d;
        return this;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Question answer(Answer answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Question createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Question updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getSendForApprovalDate() {
        return sendForApprovalDate;
    }

    public Question sendForApprovalDate(Instant sendForApprovalDate) {
        this.sendForApprovalDate = sendForApprovalDate;
        return this;
    }

    public void setSendForApprovalDate(Instant sendForApprovalDate) {
        this.sendForApprovalDate = sendForApprovalDate;
    }

    public Instant getApprovedDate() {
        return approvedDate;
    }

    public Question approvedDate(Instant approvedDate) {
        this.approvedDate = approvedDate;
        return this;
    }

    public void setApprovedDate(Instant approvedDate) {
        this.approvedDate = approvedDate;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Question questionType(QuestionType questionType) {
        this.questionType = questionType;
        return this;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Question difficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Status getStatus() {
        return status;
    }

    public Question status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Question createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public Question updatedBy(User user) {
        this.updatedBy = user;
        return this;
    }

    public void setUpdatedBy(User user) {
        this.updatedBy = user;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public Question approvedBy(User user) {
        this.approvedBy = user;
        return this;
    }

    public void setApprovedBy(User user) {
        this.approvedBy = user;
    }

    public Country getCountry() {
        return country;
    }

    public Question country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Category getCategory() {
        return category;
    }

    public Question category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Topic getTopic() {
        return topic;
    }

    public Question topic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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
        Question question = (Question) o;
        if (question.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Question{" +
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
