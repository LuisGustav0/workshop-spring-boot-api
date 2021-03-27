package com.arpiatecnologia.consts;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

public class ClienteErrorConst {

    public static final String CLIENTE_NOME_JA_CADASTRADO = "Nome já cadastrado";

    public static final HttpStatus HTTP_STATUS_BAD_REQUEST;
    public static Supplier<ResponseStatusException> BE_CLIENTE_NOT_FOUND;

    static {
        HTTP_STATUS_BAD_REQUEST = HttpStatus.BAD_REQUEST;
        BE_CLIENTE_NOT_FOUND = () -> new ResponseStatusException(HTTP_STATUS_BAD_REQUEST);
    }
}
