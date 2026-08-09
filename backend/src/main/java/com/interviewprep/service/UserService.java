package com.interviewprep.service;

import com.interviewprep.dto.UserRequest;
import com.interviewprep.dto.UserResponse;
import com.interviewprep.entity.User;
import com.interviewprep.repository.UserRepository;
import com.interviewprep.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public UserService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAllByOrderByNameAsc()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        questionRepository.findByUserIdOrderByIdAsc(user.getId()).size()
                ))
                .toList();
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        User saved = userRepository.save(new User(request.name().trim()));
        return new UserResponse(saved.getId(), saved.getName(), 0);
    }

    @Transactional(readOnly = true)
    public User getEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
