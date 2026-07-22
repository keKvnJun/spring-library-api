package com.kevin.spring_library_api.exception;

public class IsbnDuplicateException extends RuntimeException {
    public IsbnDuplicateException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}
