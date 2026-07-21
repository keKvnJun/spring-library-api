package com.kevin.spring_library_api.exception;

public class IsbnNotFoundException extends RuntimeException {
    public IsbnNotFoundException(String isbn) {
        super("Book not found with ISBN: " + isbn);
    }
}
