package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.model.dto.PedidoDto;
import br.com.alura.comex.repository.PedidoRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {


    private final MessageSource messageSource;
    private final PedidoRepository pedidoRepository;

    public PedidoService(@Lazy MessageSource messageSource, @Lazy PedidoRepository pedidoRepository) {
        this.messageSource = messageSource;
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(messageSource.getMessage("order.not-found", new Object[]{id}, LocaleContextHolder.getLocale()));
                });
    }

    @Transactional
    public Pedido inserir(PedidoDto pedidoDto) {
        return inserir(pedidoDto.convert());
    }

    @Transactional
    public Pedido inserir(Pedido pedido) {
        pedido.setId(null);
        return persist(pedido);
    }

    @Transactional
    public Pedido atualizar(PedidoDto pedidoDto) {
        return atualizar(pedidoDto.convert());
    }

    @Transactional
    public Pedido atualizar(Pedido pedido) {
        this.buscarPorId(pedido.getId());
        return atualizar(pedido);
    }

    @Transactional
    public void remover(Long id) {
        pedidoRepository.delete(this.buscarPorId(id));
    }

    protected Pedido persist(Pedido pedido) {
        return pedidoRepository.saveAndFlush(pedido);
    }
}
