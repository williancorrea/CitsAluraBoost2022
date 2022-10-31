package br.com.alura.comex.service;

import br.com.alura.comex.config.exception.BusinessException;
import br.com.alura.comex.config.exception.NotFoundException;
import br.com.alura.comex.model.*;
import br.com.alura.comex.model.dto.PedidoNovoDto;
import br.com.alura.comex.repository.PedidoRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {


    private final MessageSource messageSource;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PedidoRepository pedidoRepository;

    public PedidoService(@Lazy MessageSource messageSource,
                         @Lazy PedidoRepository pedidoRepository,
                         @Lazy ClienteService clienteService,
                         @Lazy ProdutoService produtoService) {
        this.messageSource = messageSource;
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(messageSource.getMessage("order.not-found", new Object[]{id}, LocaleContextHolder.getLocale()));
                });
    }

    @Transactional
    public Pedido inserir(PedidoNovoDto dto) {
        List<ItemDePedido> itemDePedido = new ArrayList<>();
        dto.getProdutos().forEach(item ->
                itemDePedido.add(
                        ItemDePedido.builder()
                                .produto(Produto.builder()
                                        .id(item.getProdutoId())
                                        .build())
                                .quantidade(item.getQuantidade())
                                .build()
                )
        );
        return inserir(
                Pedido.builder()
                        .cliente(Cliente.builder().id(dto.getClienteId()).build())
                        .itens(itemDePedido)
                        .build()
        );
    }

    @Transactional
    public Pedido inserir(Pedido pedido) {
        pedido.setId(null);

        clienteService.buscarPorId(pedido.getCliente().getId());

        pedido.setData(LocalDate.now());
        pedido.setDesconto(BigDecimal.ZERO);
        pedido.setTipoDesconto(TipoDesconto.NENHUM);

        pedido.getItens().forEach(item -> {
            var produto = produtoService.buscarPorId(item.getProduto().getId());
            if (produto.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new BusinessException(messageSource.getMessage("Insufficient.stock", new Object[]{produto.getNome(), item.getQuantidade()}, LocaleContextHolder.getLocale()));
            }
            item.setPrecoUnitario(produto.getPrecoUnitario());
            item.setDesconto(BigDecimal.ZERO);
            item.setTipoDesconto(TipoDescontoItem.NENHUM);
            item.setPedido(pedido);
        });
        /**
         * Conceder desconto de 10% nos itens, se haver mais de 10 unidades do mesmo produto
         */
        pedido.getItens().forEach(a -> {
            Integer soma = pedido.getItens()
                    .parallelStream()
                    .filter(b -> b.getProduto().equals(a.getProduto()))
                    .mapToInt(ItemDePedido::getQuantidade)
                    .sum();
            if (soma >= 10) {
                a.setDesconto(BigDecimal.valueOf(0.10));
                a.setTipoDesconto(TipoDescontoItem.QUANTIDADE);
            }
        });
        /**
         * Se cliente já fez 5 ou mais compras, aplicar desconto FIDELIDADE de 5 % no pedido.
         */
        if (pedidoRepository.contarPedidosPorCliente(pedido.getCliente().getId()) >= 5) {
            pedido.setTipoDesconto(TipoDesconto.FIDELIDADE);
            pedido.setDesconto(BigDecimal.valueOf(0.05));
        }
        pedidoRepository.saveAndFlush(pedido);
        /**
         * Aplicando baixa no estoque
         */
        pedido.getItens().forEach(a -> {
            a.getProduto().setQuantidadeEstoque(a.getProduto().getQuantidadeEstoque() - a.getQuantidade());
            produtoService.atualizar(a.getProduto());
        });
        return pedido;
    }
}
