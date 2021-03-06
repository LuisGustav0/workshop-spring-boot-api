package com.arpiatecnologia.service;

import com.arpiatecnologia.exception.BusinessException;
import com.arpiatecnologia.exception.ClienteNotFoundException;
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

import java.util.Optional;

import static com.arpiatecnologia.consts.ClienteErrorConst.CLIENTE_NOME_JA_CADASTRADO;
import static com.arpiatecnologia.consts.ClienteErrorConst.CLIENTE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("Deve lan??ar um erro de negocio ao tentar salvar um cliente com nome duplicado")
    void save_ClienteComNomeDuplicado() {
        Cliente fakeCliente = this.fakeNewCliente();

        when(this.repository.existsByNome(anyString())).thenReturn(true);

        Throwable throwable = catchThrowable(() -> this.service.save(fakeCliente));

        assertThat(throwable).isInstanceOf(BusinessException.class).hasMessage(CLIENTE_NOME_JA_CADASTRADO);
    }

    @Test
    @DisplayName("Deve deletar um cliente com sucesso")
    void delete_ClienteById() {
        Long id = 1L;

        Cliente cliente = this.fakeSavedCliente();

        when(this.repository.findById(id)).thenReturn(Optional.of(cliente));

        this.service.deleteById(id);

        verify(this.repository, times(1)).delete(cliente);
    }

    @Test
    @DisplayName("Deve retornar um erro ao tentar deletar cliente n??o encontrado")
    void delete_ClienteById_NotFound() {
        Long id = 1L;

        when(this.repository.findById(id)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> this.service.deleteById(id));

        assertThat(throwable).isInstanceOf(ClienteNotFoundException.class).hasMessage(CLIENTE_NOT_FOUND);
    }
}
