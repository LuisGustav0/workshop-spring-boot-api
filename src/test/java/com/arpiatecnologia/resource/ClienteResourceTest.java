package com.arpiatecnologia.resource;

import com.arpiatecnologia.model.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    @Test
    @DisplayName("Deve criar um cliente com sucesso.")
    void create_Sucesso() throws Exception {
        Cliente cliente = Cliente.builder().nome("Luis Gustavo").build();

        String json = this.mapper.writeValueAsString(cliente);

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
