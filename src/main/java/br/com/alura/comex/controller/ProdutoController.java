package br.com.alura.comex.controller;

import br.com.alura.comex.model.Produto;
import br.com.alura.comex.model.dto.ClienteDto;
import br.com.alura.comex.model.dto.ProdutoDto;
import br.com.alura.comex.service.ProdutoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
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
    public List<ProdutoDto> listar( @PageableDefault(sort = {"nome"}, direction = Sort.Direction.ASC, value = 5) Pageable pageable) {
        return produtoService.listarTodos(pageable)
                .stream().toList()
                .parallelStream()
                .map(ProdutoDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ProdutoDto> cadastrar(@RequestBody @Valid ProdutoDto produtoDto, UriComponentsBuilder uriBuilder) {
        Produto produto = produtoService.inserir(produtoDto);
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDto(produto));
    }
}
