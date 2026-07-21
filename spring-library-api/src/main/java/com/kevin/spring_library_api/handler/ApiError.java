package com.kevin.spring_library_api.handler;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<FieldError> fieldErrors
) {}