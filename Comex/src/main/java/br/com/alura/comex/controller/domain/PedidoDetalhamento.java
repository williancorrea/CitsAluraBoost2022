package br.com.alura.comex.controller.domain;

import br.com.alura.comex.model.ItemDePedido;
import br.com.alura.comex.model.Pedido;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PedidoDetalhamento {

    private LocalDate dataDoPedido;
    private BigDecimal valor;
    private BigDecimal desconto;
    private List<PedidoDetalhamentoItem> items;
    private PedidoDetalhamentoCliente cliente;

    @Getter
    class PedidoDetalhamentoItem {
        private Long ItemId;
        private Long produtoId;
        private String Nome;
        private String categoria;
        private Integer quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal valor;
        private BigDecimal desconto;

        public PedidoDetalhamentoItem(ItemDePedido item) {
            this.ItemId = item.getId();
            this.produtoId = item.getProduto().getId();
            this.Nome = item.getProduto().getNome();
            this.categoria = item.getProduto().getCategoria().getNome();
            this.quantidade = item.getQuantidade();
            this.precoUnitario = item.getPrecoUnitario();
            this.valor = item.getValorTotalComDesconto();
            this.desconto = item.getValorDescontos();
        }
    }

    @Getter
    class PedidoDetalhamentoCliente {
        private Long id;
        private String nome;

        public PedidoDetalhamentoCliente(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }
    }

    public PedidoDetalhamento(Pedido pedido) {
        this.dataDoPedido = pedido.getData();

        this.cliente = new PedidoDetalhamentoCliente(
                pedido.getCliente().getId(),
                pedido.getCliente().getNome());

        // Soma todos os descontos (Pedido e itens)
        this.desconto =
                BigDecimal.valueOf(
                        pedido.getItens()
                                .parallelStream()
                                .mapToDouble(a -> a.getValorDescontos().doubleValue())
                                .sum()).add(pedido.getValorDesconto()
                );

        // Valor pago com todos os descontos
        this.valor = pedido.getValorPedidoComDesconto();
        this.items = pedido.getItens().stream().map(PedidoDetalhamentoItem::new).collect(Collectors.toList());
    }
}
