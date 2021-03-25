package com.arpiatecnologia.service;

import com.arpiatecnologia.model.Cliente;
import com.arpiatecnologia.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ClienteServiceTest {

    private ClienteService service;

    @BeforeEach
    public void setup() {
        this.service = new ClienteServiceImpl();
    }

    private Cliente fakeNewCliente() {
        return Cliente.builder().nome("Luis Gustavo").build();
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    void save_Sucesso() {
        Cliente cliente = this.fakeNewCliente();
        
        Cliente clienteSaved = this.service.save(cliente);

        assertThat(clienteSaved.getId()).isNotNull();
        assertThat(clienteSaved.getNome()).isEqualTo("Luis Gustavo");
    }
}
