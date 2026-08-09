package com.interviewprep.repository;

import com.interviewprep.entity.Question;
import com.interviewprep.entity.QuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByUserIdOrderByIdAsc(Long userId);
    List<Question> findByUserIdAndStatusOrderByIdAsc(Long userId, QuestionStatus status);
    void deleteByUserId(Long userId);
}
