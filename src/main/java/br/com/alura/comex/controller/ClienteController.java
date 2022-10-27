package br.com.alura.comex.controller;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.dto.ClienteDto;
import br.com.alura.comex.service.ClienteService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(@Lazy ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RequestMapping
    public List<ClienteDto> listar() {
        return clienteService.listarTodos().parallelStream().map(ClienteDto::new).collect(Collectors.toList());
    }

    @RequestMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarPorNome(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

//    @RequestMapping("/filtro")
//    public List<Cliente> buscarPorNome(@Param("nome") String nome) {
//        return clienteService.findByNome(nome);
//    }

    @PostMapping
    public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteDto clienteDto, UriComponentsBuilder uriBuilder) {
        Cliente cliente = clienteService.inserir(clienteDto);
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
        clienteService.remover(id);
    }
}
