package br.com.alura.comex.controller;

import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.dto.CategoriaDto;
import br.com.alura.comex.model.dto.CategoriaDto;
import br.com.alura.comex.model.projections.CategoriaPedidosProjection;
import br.com.alura.comex.service.CategoriaService;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(@Lazy CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> cadastrar(@RequestBody @Valid CategoriaDto clienteDto, UriComponentsBuilder uriBuilder) {
        Categoria categoria = categoriaService.inserir(clienteDto);
        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
    }

    @GetMapping("/pedidos")
    public List<CategoriaPedidosProjection> pedidos() {
        return categoriaService.pedidos();
    }
}
