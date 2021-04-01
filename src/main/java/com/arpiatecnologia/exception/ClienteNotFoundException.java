package com.arpiatecnologia.exception;

public class ClienteNotFoundException extends EntityNotFoundException {

    public ClienteNotFoundException(String mensagem) {
        super(mensagem);
    }
}
