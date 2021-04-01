package com.arpiatecnologia.repository;

import com.arpiatecnologia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteQueryRepository {

    boolean existsByNome(String nome);
}
