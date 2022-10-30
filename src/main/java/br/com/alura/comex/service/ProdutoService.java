package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.model.dto.ProdutoDto;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(@Lazy ProdutoRepository produtoRepository,
                          @Lazy CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public Page<Produto> listarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException();
                });
    }

    @Transactional
    public Produto inserir(ProdutoDto produtoDto) {
        return inserir(produtoDto.convert());
    }

    @Transactional
    public Produto inserir(Produto produto) {
        produto.setId(null);
        return persist(produto);
    }

    @Transactional
    public Produto atualizar(ProdutoDto produtoDto) {
        return atualizar(produtoDto.convert());
    }

    @Transactional
    public Produto atualizar(Produto produto) {
        this.buscarPorId(produto.getId());
        return persist(produto);
    }

    @Transactional
    public void remover(Long id) {
        produtoRepository.delete(this.buscarPorId(id));
    }

    protected Produto persist(Produto produto) {
        categoriaService.buscarPorId(produto.getCategoria().getId());
        return produtoRepository.saveAndFlush(produto);
    }
}
