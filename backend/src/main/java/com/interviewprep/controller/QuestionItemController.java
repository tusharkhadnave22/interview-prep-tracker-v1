package com.interviewprep.controller;

import com.interviewprep.dto.QuestionRequest;
import com.interviewprep.dto.QuestionResponse;
import com.interviewprep.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionItemController {

    private final QuestionService questionService;

    public QuestionItemController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PutMapping("/{id}")
    public QuestionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequest request) {
        return questionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
