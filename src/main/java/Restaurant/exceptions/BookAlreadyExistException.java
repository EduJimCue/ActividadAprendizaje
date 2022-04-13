package com.svalero.books.exception;

public class BookAlreadyExistException extends Exception {

    public BookAlreadyExistException(String message) {
        super(message);
    }

    public BookAlreadyExistException() {
        super("El libro ya existe");
    }
}
