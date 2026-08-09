package com.interviewprep.controller;

import com.interviewprep.dto.QuestionRequest;
import com.interviewprep.dto.QuestionResponse;
import com.interviewprep.entity.QuestionStatus;
import com.interviewprep.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionResponse> getQuestions(
            @PathVariable Long userId,
            @RequestParam(required = false) QuestionStatus status) {
        return questionService.getByUser(userId, status);
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> create(
            @PathVariable Long userId,
            @Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.create(userId, request));
    }
}
