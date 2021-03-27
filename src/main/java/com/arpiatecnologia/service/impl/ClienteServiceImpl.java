package com.arpiatecnologia.service.impl;

import com.arpiatecnologia.exception.BusinessException;
import com.arpiatecnologia.model.Cliente;
import com.arpiatecnologia.repository.ClienteRepository;
import com.arpiatecnologia.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.arpiatecnologia.consts.ClienteErrorConst.BE_CLIENTE_NOT_FOUND;
import static com.arpiatecnologia.consts.ClienteErrorConst.CLIENTE_NOME_JA_CADASTRADO;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    public boolean existsByNome(String nome) {
        return this.repository.existsByNome(nome);
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (this.existsByNome(cliente.getNome()))
            throw new BusinessException(CLIENTE_NOME_JA_CADASTRADO);

        return this.repository.save(cliente);
    }

    @Override
    public void deleteById(Long id) {
        Cliente cliente = this.readById(id).orElseThrow(BE_CLIENTE_NOT_FOUND);

        this.repository.delete(cliente);
    }

    @Override
    public Optional<Cliente> readById(Long id) {
        return this.repository.findById(id);
    }
}
