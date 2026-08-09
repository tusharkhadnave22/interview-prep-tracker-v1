package com.interviewprep.service;

import com.interviewprep.dto.QuestionRequest;
import com.interviewprep.dto.QuestionResponse;
import com.interviewprep.entity.Question;
import com.interviewprep.entity.QuestionStatus;
import com.interviewprep.entity.User;
import com.interviewprep.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;

    public QuestionService(QuestionRepository questionRepository, UserService userService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getByUser(Long userId, QuestionStatus status) {
        List<Question> questions = status == null
                ? questionRepository.findByUserIdOrderByIdAsc(userId)
                : questionRepository.findByUserIdAndStatusOrderByIdAsc(userId, status);

        if (!questions.isEmpty() || userServiceExists(userId)) {
            return questions.stream().map(this::toResponse).toList();
        }
        throw new EntityNotFoundException("User not found: " + userId);
    }

    private boolean userServiceExists(Long userId) {
        try {
            userService.getEntity(userId);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }
    }

    @Transactional
    public QuestionResponse create(Long userId, QuestionRequest request) {
        User user = userService.getEntity(userId);
        Question question = new Question(request.questionText().trim(), user);
        if (request.status() != null) {
            question.setStatus(request.status());
        }
        return toResponse(questionRepository.save(question));
    }

    @Transactional
    public QuestionResponse update(Long questionId, QuestionRequest request) {
        Question question = getEntity(questionId);
        question.setQuestionText(request.questionText().trim());
        question.setStatus(request.status() == null ? QuestionStatus.NOT_STARTED : request.status());
        return toResponse(questionRepository.save(question));
    }

    @Transactional
    public void delete(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Question not found: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }

    public Question getEntity(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found: " + id));
    }

    private QuestionResponse toResponse(Question q) {
        return new QuestionResponse(q.getId(), q.getUser().getId(), q.getQuestionText(), q.getStatus());
    }
}
