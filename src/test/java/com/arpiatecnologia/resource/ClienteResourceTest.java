package com.arpiatecnologia.resource;

import com.arpiatecnologia.exception.BusinessException;
import com.arpiatecnologia.model.Cliente;
import com.arpiatecnologia.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.arpiatecnologia.consts.ClienteErrorConst.CLIENTE_NOME_JA_CADASTRADO;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
class ClienteResourceTest {

    static String CLIENTE_API = "/cliente";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClienteService service;

    private Cliente fakeSavedCliente(Long id, String nome) {
        return Cliente.builder().id(id).nome(nome).build();
    }

    private Cliente fakeNewCliente(String nome) {
        return this.fakeSavedCliente(null, nome);
    }

    private Cliente fakeNewCliente() {
        return this.fakeNewCliente(null);
    }

    private MockHttpServletRequestBuilder mockPost(String json) {
        return MockMvcRequestBuilders.post(CLIENTE_API)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(json);
    }

    private MockHttpServletRequestBuilder mockGetById(Long id) {
        String url = CLIENTE_API.concat("/" + id);

        return MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Deve criar um cliente com sucesso.")
    void create_ClienteComSucesso() throws Exception {
        Cliente fakeCliente = this.fakeNewCliente("Luis Gustavo");
        Cliente fakeClienteSaved = this.fakeSavedCliente(13L, "Luis Gustavo");

        String json = this.mapper.writeValueAsString(fakeCliente);

        given(this.service.save(any(Cliente.class))).willReturn(fakeClienteSaved);

        MockHttpServletRequestBuilder request = this.mockPost(json);

        this.mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("nome").value(fakeCliente.getNome()));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não enviar nome do cliente")
    void create_Cliente_NomeVazio() throws Exception {
        Cliente fakeCliente = this.fakeNewCliente();

        String json = this.mapper.writeValueAsString(fakeCliente);

        MockHttpServletRequestBuilder request = this.mockPost(json);

        this.mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("errors", hasSize(1)));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar criar um cliente com nome que já possui")
    void create_ClienteComNomeDuplicado() throws Exception {
        Cliente fakeCliente = this.fakeNewCliente("Luis Gustavo");
        String json = this.mapper.writeValueAsString(fakeCliente);

        given(this.service.save(any(Cliente.class))).willThrow(new BusinessException(CLIENTE_NOME_JA_CADASTRADO));

        MockHttpServletRequestBuilder request = this.mockPost(json);

        this.mockMvc.perform(request)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("errors", hasSize(1)))
                    .andExpect(jsonPath("errors[0].message").value(CLIENTE_NOME_JA_CADASTRADO));
    }

    @Test
    @DisplayName("Deve obter informações de um cliente")
    void read_ClienteById() throws Exception {
        Long id = 1L;

        Cliente cliente = this.fakeSavedCliente(id, "Luis Gustavo");

        given(this.service.readById(anyLong())).willReturn(Optional.of(cliente));

        MockHttpServletRequestBuilder request = this.mockGetById(id);

        this.mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(id))
                    .andExpect(jsonPath("nome").value(cliente.getNome()));
    }

    @Test
    @DisplayName("Deve retornar not found quando o cliente procurado não existir")
    void read_ClienteById_NotFound() throws Exception {
        Long id = 1L;

        given(this.service.readById(anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = this.mockGetById(id);

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }
}
