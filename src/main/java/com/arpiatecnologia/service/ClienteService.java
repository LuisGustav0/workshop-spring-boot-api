package com.arpiatecnologia.service;

import com.arpiatecnologia.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Cliente save(Cliente cliente);

    Optional<Cliente> readById(Long id);

    void deleteById(Long id);

    Cliente update(Long id, Cliente cliente);

    List<Cliente> readAll();
}
