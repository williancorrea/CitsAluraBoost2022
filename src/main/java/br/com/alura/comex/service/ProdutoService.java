package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.model.dto.ProdutoDto;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final MessageSource messageSource;
    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(@Lazy MessageSource messageSource,
                          @Lazy ProdutoRepository produtoRepository,
                          @Lazy CategoriaService categoriaService) {
        this.messageSource = messageSource;
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public Page<Produto> listarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(messageSource.getMessage("product.not-found", new Object[]{id}, LocaleContextHolder.getLocale()));
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
