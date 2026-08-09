package com.interviewprep.dto;

public record UserResponse(
        Long id,
        String name,
        long questionCount
) {}
