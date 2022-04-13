package com.svalero.books.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("Datos de usuario incorrectos");
    }
}
