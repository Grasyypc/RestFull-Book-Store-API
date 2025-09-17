package com.backend.exception;

public class BooksAndAuthorNotFound extends RuntimeException {
    public BooksAndAuthorNotFound(String message) {

        super(message);
    }
}
