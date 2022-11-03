package br.com.alura.comex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "itens_pedido")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemDePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pedido pedido;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto produto;

    @Column(nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private TipoDescontoItem tipoDesconto = TipoDescontoItem.NENHUM;

    public ItemDePedido() {
        super();
    }

    public ItemDePedido(Integer quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.precoUnitario = produto.getPrecoUnitario();
    }

    public BigDecimal getValorTotalItem() {
        return this.precoUnitario.multiply(new BigDecimal(this.quantidade));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemDePedido that = (ItemDePedido) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}