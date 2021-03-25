package com.arpiatecnologia.resource;

import com.arpiatecnologia.model.Cliente;
import com.arpiatecnologia.service.ClienteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteResource {

    private final ClienteService service;

    private URI getLocation(@NonNull Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente clienteSaved = this.service.save(cliente);
        URI uriLocation = this.getLocation(clienteSaved.getId());

        return ResponseEntity.created(uriLocation).body(clienteSaved);
    }
}
