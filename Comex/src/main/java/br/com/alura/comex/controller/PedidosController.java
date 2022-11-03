package br.com.alura.comex.controller;

import br.com.alura.comex.model.dto.PedidoNovoDto;
import br.com.alura.comex.service.PedidoService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final PedidoService pedidoService;

    public PedidosController(@Lazy PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @CacheEvict(value = "listaCategoriasPedidos", allEntries = true)
    public ResponseEntity<PedidoNovoDto> cadastrar(@RequestBody @Valid PedidoNovoDto dto) {
        pedidoService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
