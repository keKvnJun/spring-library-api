package com.kevin.spring_library_api.handler;

import com.kevin.spring_library_api.exception.BookNotFoundException;
import com.kevin.spring_library_api.exception.IsbnDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Connection SQL Exception
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiError> handleSqlException(SQLException ex) {
        var error = new ApiError(LocalDateTime.now(), 503, "SERVICE_UNAVAILABLE", "SQL connection error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // Generic data access exception
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDataAccessException(DataAccessException ex) {
        var error = new ApiError(LocalDateTime.now(), 500, "INTERNAL_SERVER_ERROR", "Data access error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // Generic fallback
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        var error = new ApiError(LocalDateTime.now(), 500, "INTERNAL_SERVER_ERROR", "Unexpected error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // Book not found exception
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException ex) {
        var error = new ApiError(LocalDateTime.now(), 404, "Book not found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    // ISBN duplicate exception
    @ExceptionHandler(IsbnDuplicateException.class)
    public ResponseEntity<ApiError> handleIsbnDuplicateException(IsbnDuplicateException ex) {
        var error = new ApiError(LocalDateTime.now(), 409, "ISBN duplicate", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    // Validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var error = new ApiError(LocalDateTime.now(), 404, "Bad Request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
