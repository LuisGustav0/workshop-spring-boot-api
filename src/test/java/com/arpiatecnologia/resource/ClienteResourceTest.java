package com.arpiatecnologia.resource;

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

import static org.mockito.ArgumentMatchers.any;
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

    private Cliente fakeSavedCliente() {
        return Cliente.builder().id(1L).nome("Luis Gustavo").build();
    }

    private Cliente fakeNewCliente() {
        return Cliente.builder().nome("Luis Gustavo").build();
    }

    @Test
    @DisplayName("Deve criar um cliente com sucesso.")
    void create_Sucesso() throws Exception {
        Cliente cliente = this.fakeNewCliente();
        Cliente savedCliente = this.fakeSavedCliente();

        String json = this.mapper.writeValueAsString(cliente);

        given(this.service.save(any(Cliente.class))).willReturn(savedCliente);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API)
                                                                      .contentType(MediaType.APPLICATION_JSON)
                                                                      .accept(MediaType.APPLICATION_JSON)
                                                                      .content(json);

        this.mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("nome").value(cliente.getNome()));
    }
}
