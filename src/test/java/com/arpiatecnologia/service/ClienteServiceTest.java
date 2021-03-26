package com.arpiatecnologia.service;

import com.arpiatecnologia.model.Cliente;
import com.arpiatecnologia.repository.ClienteRepository;
import com.arpiatecnologia.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    private ClienteService service;

    @BeforeEach
    void setup() {
        this.service = new ClienteServiceImpl(repository);
    }

    private Cliente fakeSavedCliente() {
        return Cliente.builder().id(1L).nome("Luis Gustavo").build();
    }

    private Cliente fakeNewCliente() {
        return Cliente.builder().nome("Luis Gustavo").build();
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    void save_Sucesso() {
        Cliente fakeCliente = this.fakeNewCliente();
        Cliente fakeClienteSaved = this.fakeSavedCliente();

        when(this.repository.save(any(Cliente.class))).thenReturn(fakeClienteSaved);

        Cliente clienteSaved = this.service.save(fakeCliente);

        assertThat(clienteSaved.getId()).isNotNull();
        assertThat(clienteSaved.getNome()).isEqualTo("Luis Gustavo");
    }
}
