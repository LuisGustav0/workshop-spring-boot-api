package com.arpiatecnologia.repository;

import com.arpiatecnologia.filter.ClienteFilter;
import com.arpiatecnologia.model.Cliente;

import java.util.List;

public interface ClienteQueryRepository {

    List<Cliente> readAll(ClienteFilter filter);
}
