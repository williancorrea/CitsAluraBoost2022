package br.com.alura.comex.controller;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.ClienteDto;
import br.com.alura.comex.service.ClienteService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(@Lazy ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RequestMapping
    public List<Cliente> listar() {
        //TODO: Retornar o DTO
        return clienteService.findAll();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarPorNome(@PathVariable("id") Long id) {
        //TODO: Retornar o DTO
        Cliente c = clienteService.findByID(id);
        return ResponseEntity.ok().body(new ClienteDto(c));
    }

    @RequestMapping("/filtro")
    public List<Cliente> buscarPorNome(@Param("nome") String nome) {
        //TODO: Retornar o DTO
        return clienteService.findByNome(nome);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteDto clienteDto, UriComponentsBuilder uriBuilder) {
        Cliente cliente = clienteService.cadastrar(clienteDto);
        URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteDto(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> cadastrar(@PathVariable("id") @NotNull Long id, @RequestBody @Valid ClienteDto clienteDto) {
        clienteDto.setId(id);
        Cliente cliente = clienteService.atualizar(clienteDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ClienteDto(cliente));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void cadastrar(@PathVariable("id") @NotNull Long id) {
        clienteService.deletar(id);
    }
}
