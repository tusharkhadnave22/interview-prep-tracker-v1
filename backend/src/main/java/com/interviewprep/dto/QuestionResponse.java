package com.interviewprep.dto;

import com.interviewprep.entity.QuestionStatus;

public record QuestionResponse(
        Long id,
        Long userId,
        String questionText,
        QuestionStatus status
) {}
