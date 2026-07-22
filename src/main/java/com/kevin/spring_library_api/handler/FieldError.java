package com.kevin.spring_library_api.handler;

public record FieldError(
        String field,
        String message
) {}
