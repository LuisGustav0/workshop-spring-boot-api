package com.arpiatecnologia.repository;

import com.arpiatecnologia.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ClienteRepository repository;

    private Cliente fakeNewCliente(String nome) {
        return Cliente.builder().nome(nome).build();
    }

    @Test
    @DisplayName("Deve retornar true quando existir um cliente na base com nome informado")
    void existsByNome_RetornaTrue() {
        Cliente cliente = this.fakeNewCliente("Arpia Tecnologia");

        this.em.persist(cliente);

        boolean exists = this.repository.existsByNome("Arpia Tecnologia");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não existir um cliente na base com nome informado")
    void existsByNome_RetornaFalse() {
        boolean exists = this.repository.existsByNome("Arpia Tecnologia");

        assertThat(exists).isFalse();
    }
}
