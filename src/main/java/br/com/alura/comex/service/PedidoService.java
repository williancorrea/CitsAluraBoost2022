package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.model.dto.PedidoDto;
import br.com.alura.comex.repository.PedidoRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(@Lazy PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException();
                });
    }

    @Transactional
    public Pedido inserir(PedidoDto pedidoDto) {
        pedidoDto.setId(null);
        return persist(pedidoDto.convert());
    }

    @Transactional
    public Pedido inserir(Pedido pedido) {
        pedido.setId(null);
        return persist(pedido);
    }

    @Transactional
    public Pedido atualizar(PedidoDto pedidoDto) {
        this.buscarPorId(pedidoDto.getId());
        return persist(pedidoDto.convert());
    }

    @Transactional
    public Pedido atualizar(Pedido pedido) {
        this.buscarPorId(pedido.getId());
        return persist(pedido);
    }

    @Transactional
    public void remover(Long id) {
        pedidoRepository.delete(this.buscarPorId(id));
    }

    /**
     * Metodo utilizado para efetuar validações e persistencia na base de dados
     *
     * @param pedido
     * @return
     */
    protected Pedido persist(Pedido pedido) {
        return pedidoRepository.saveAndFlush(pedido);
    }
}
