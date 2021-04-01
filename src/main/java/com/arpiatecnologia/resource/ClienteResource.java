package com.arpiatecnologia.resource;

import com.arpiatecnologia.model.Cliente;
import com.arpiatecnologia.service.ClienteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Cliente> create(@RequestBody @Valid Cliente cliente) {
        Cliente clienteSaved = this.service.save(cliente);
        URI uriLocation = this.getLocation(clienteSaved.getId());

        return ResponseEntity.created(uriLocation).body(clienteSaved);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
        return this.service.update(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.service.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> readById(@PathVariable Long id) {
        Optional<Cliente> optCliente = this.service.readById(id);

        return optCliente.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cliente> readAll() {
        return this.service.readAll();
    }
}
