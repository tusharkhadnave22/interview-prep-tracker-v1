package com.interviewprep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions", indexes = {
        @Index(name = "idx_question_user", columnList = "user_id"),
        @Index(name = "idx_question_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private QuestionStatus status = QuestionStatus.NOT_STARTED;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Question(String questionText, User user) {
        this.questionText = questionText;
        this.user = user;
    }
}
