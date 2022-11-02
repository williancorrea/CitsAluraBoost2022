package br.com.alura.comex.controller;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.dto.ClienteDto;
import br.com.alura.comex.model.dto.ClienteListDto;
import br.com.alura.comex.service.ClienteService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @Cacheable(value = "listaClientes")
    public List<ClienteListDto> listar(@PageableDefault(sort = {"nome"}, direction = Sort.Direction.ASC, value = 5) Pageable pageable) {
        return clienteService.listarTodos(pageable).getContent().parallelStream().map(ClienteListDto::new).collect(Collectors.toList());
    }

    @RequestMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarPorNome(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    @PostMapping
    @CacheEvict(value = "listaClientes", allEntries = true)
    public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteDto clienteDto, UriComponentsBuilder uriBuilder) {
        Cliente cliente = clienteService.inserir(clienteDto);
        URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteDto(cliente));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaClientes", allEntries = true)
    public ResponseEntity<ClienteDto> cadastrar(@PathVariable("id") @NotNull Long id, @RequestBody @Valid ClienteDto clienteDto) {
        clienteDto.setId(id);
        Cliente cliente = clienteService.atualizar(clienteDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ClienteDto(cliente));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaClientes", allEntries = true)
    public void cadastrar(@PathVariable("id") @NotNull Long id) {
        clienteService.remover(id);
    }
}
