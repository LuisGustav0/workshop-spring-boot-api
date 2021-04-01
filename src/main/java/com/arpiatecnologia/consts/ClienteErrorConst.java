package com.arpiatecnologia.consts;

import com.arpiatecnologia.exception.ClienteNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class ClienteErrorConst {

    public static final String CLIENTE_NOME_JA_CADASTRADO = "Nome já cadastrado";

    public static final String CLIENTE_NOT_FOUND;
    public static Supplier<ClienteNotFoundException> BE_CLIENTE_NOT_FOUND;

    static {
        CLIENTE_NOT_FOUND = "Cliente não encontrado";
        BE_CLIENTE_NOT_FOUND = () -> new ClienteNotFoundException(CLIENTE_NOT_FOUND);
    }
}
