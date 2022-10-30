package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.StatusCategoria;
import br.com.alura.comex.model.dto.CategoriaDto;
import br.com.alura.comex.model.projections.CategoriaPedidosProjection;
import br.com.alura.comex.repository.CategoriaRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(@Lazy CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException();
                });
    }

    @Transactional
    public Categoria inserir(CategoriaDto categoriaDto) {
        return inserir(categoriaDto.convert());
    }

    @Transactional
    public Categoria inserir(Categoria categoria) {
        categoria.setId(null);
        categoria.setStatus(StatusCategoria.ATIVA);
        return persist(categoria);
    }

    @Transactional
    public Categoria atualizar(CategoriaDto categoriaDto) {
        return atualizar(categoriaDto.convert());
    }

    @Transactional
    public Categoria atualizar(Categoria categoria) {
        this.buscarPorId(categoria.getId());
        return persist(categoria);
    }

    @Transactional
    public void remover(Long id) {
        categoriaRepository.delete(this.buscarPorId(id));
    }

    protected Categoria persist(Categoria categoria) {
        return categoriaRepository.saveAndFlush(categoria);
    }

    public List<CategoriaPedidosProjection> pedidos(){
        return categoriaRepository.relatorioPedidos();
    }
}
