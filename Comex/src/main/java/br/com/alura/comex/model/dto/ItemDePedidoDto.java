package br.com.alura.comex.model.dto;

import br.com.alura.comex.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
public class ItemDePedidoDto {

    private Long id;
    @NotNull
    @NotEmpty
    private BigDecimal precoUnitario;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Long pedidoId;
    @NotNull
    private Long produtoId;

    public ItemDePedidoDto() {
    }

    public ItemDePedidoDto(ItemDePedido item) {
        this.id = item.getId();
        this.precoUnitario = item.getPrecoUnitario();
        this.quantidade = item.getQuantidade();
        this.pedidoId = item.getPedido().getId();
        this.produtoId = item.getProduto().getId();
    }

    public ItemDePedido convert() {
        //TODO: Nao foi passado o tipo de desconto
        return ItemDePedido.builder()
                .id(this.id)
                .precoUnitario(this.precoUnitario)
                .quantidade(this.quantidade)
                .pedido(Pedido.builder()
                        .id(this.pedidoId)
                        .build())
                .produto(Produto.builder()
                        .id(this.produtoId)
                        .build())
                .build();
    }
}