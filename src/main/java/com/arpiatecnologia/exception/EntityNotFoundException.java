package com.arpiatecnologia.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String mensagem) {
        super(mensagem);
    }
}
