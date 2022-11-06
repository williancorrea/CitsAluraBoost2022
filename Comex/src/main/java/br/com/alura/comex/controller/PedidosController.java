package br.com.alura.comex.controller;

import br.com.alura.comex.controller.domain.PedidoDetalhamento;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.model.dto.ClienteDto;
import br.com.alura.comex.model.dto.PedidoNovoDto;
import br.com.alura.comex.service.PedidoService;
import br.com.alura.comex.service.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final PedidoService pedidoService;
    private final TokenService tokenService;

    public PedidosController(@Lazy PedidoService pedidoService,
                             @Lazy TokenService tokenService) {
        this.pedidoService = pedidoService;
        this.tokenService= tokenService;
    }

    @PostMapping
    @CacheEvict(value = "listaCategoriasPedidos", allEntries = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PedidoNovoDto> cadastrar(@RequestBody @Valid PedidoNovoDto dto) {
        pedidoService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @RequestMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PedidoDetalhamento> buscarPorId(@PathVariable("id") Long id, @RequestHeader HttpHeaders headers) {
        var usuarioID = tokenService.getIdUsuario(headers);
        Pedido pedido = pedidoService.buscarPorId(id, usuarioID);
        return ResponseEntity.ok().body(new PedidoDetalhamento(pedido));
    }
}
