package com.interviewprep.dto;

import com.interviewprep.entity.QuestionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionRequest(
        @NotBlank(message = "Question text is required")
        @Size(max = 1000, message = "Question must be at most 1000 characters")
        String questionText,
        QuestionStatus status
) {}
