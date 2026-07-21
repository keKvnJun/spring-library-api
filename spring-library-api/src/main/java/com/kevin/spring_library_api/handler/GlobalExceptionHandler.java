package com.kevin.spring_library_api.handler;

import com.kevin.spring_library_api.exception.BookNotFoundException;
import com.kevin.spring_library_api.exception.IsbnDuplicateException;
import com.kevin.spring_library_api.exception.IsbnNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Connection SQL Exception
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiError> handleSqlException(SQLException ex) {
        var error = new ApiError(LocalDateTime.now(), 503, "SERVICE_UNAVAILABLE", "Database service is temporarily unavailable", null);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    // Generic data access exception
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDataAccessException(DataAccessException ex) {
        var error = new ApiError(LocalDateTime.now(), 500, "INTERNAL_SERVER_ERROR", "Unable to process the request due to a database error", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // Generic fallback
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        var error = new ApiError(LocalDateTime.now(), 500, "INTERNAL_SERVER_ERROR", "An unexpected internal error occurred", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // Book not found exception
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException ex) {
        var error = new ApiError(LocalDateTime.now(), 404, "Book not found", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    // ISBN duplicate exception
    @ExceptionHandler(IsbnDuplicateException.class)
    public ResponseEntity<ApiError> handleIsbnDuplicateException(IsbnDuplicateException ex) {
        var error = new ApiError(LocalDateTime.now(), 409, "ISBN duplicate", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    // ISBN not found exception
    @ExceptionHandler(IsbnNotFoundException.class)
    public ResponseEntity<ApiError> handleIsbnNotFoundException(IsbnNotFoundException ex) {
        var error = new ApiError(LocalDateTime.now(), 404, "ISBN not found", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    // Validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var error = new ApiError(LocalDateTime.now(), 400, "Bad Request", "One or more fields are invalid", ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    // Method argument type mismatch exception
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        var error = new ApiError(LocalDateTime.now(), 400, "Bad Request", "Invalid value for parameter: " + ex.getName(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
