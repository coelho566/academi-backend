package com.projeto.academia.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String menssage) {
        super(menssage);
    }
}
