package br.com.alura.comex.controller;

import br.com.alura.comex.controller.domain.ProdutoAtualizar;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.model.dto.ClienteDto;
import br.com.alura.comex.model.dto.ProdutoDto;
import br.com.alura.comex.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(@Lazy ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @RequestMapping
    @Cacheable(value = "listaProdutos")
    public List<ProdutoDto> listar(@PageableDefault(sort = {"nome"}, direction = Sort.Direction.ASC, value = 5) Pageable pageable) {
        return produtoService.listarTodos(pageable)
                .stream().toList()
                .parallelStream()
                .map(ProdutoDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    @CacheEvict(value = "listaProdutos", allEntries = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProdutoDto> cadastrar(@RequestBody @Valid ProdutoDto produtoDto, UriComponentsBuilder uriBuilder) {
        Produto produto = produtoService.inserir(produtoDto);
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDto(produto));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaProdutos", allEntries = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProdutoDto> atualizar(@PathVariable("id") @NotNull Long id, @RequestBody @Valid ProdutoDto dto) {
        dto.setId(id);
        Produto produto = produtoService.atualizar(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ProdutoDto(produto));
    }
}
