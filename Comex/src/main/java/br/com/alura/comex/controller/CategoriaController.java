package br.com.alura.comex.controller;

import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.dto.CategoriaDto;
import br.com.alura.comex.model.projections.CategoriaPedidosProjection;
import br.com.alura.comex.service.CategoriaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
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
    @CacheEvict(value = "listaCategorias", allEntries = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CategoriaDto> cadastrar(@RequestBody @Valid CategoriaDto clienteDto, UriComponentsBuilder uriBuilder) {
        Categoria categoria = categoriaService.inserir(clienteDto);
        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
    }

    @GetMapping
    public List<CategoriaDto> listar(@PageableDefault(sort = {"nome"}, direction = Sort.Direction.ASC, value = 5) Pageable pageable) {
        return categoriaService.listarTodos(pageable)
                .stream()
                .toList()
                .parallelStream()
                .map(CategoriaDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/pedidos")
    @Cacheable(value = "listaCategoriasPedidos")
    @SecurityRequirement(name = "bearerAuth")
    public List<CategoriaPedidosProjection> pedidos() {
        return categoriaService.pedidos();
    }
}
