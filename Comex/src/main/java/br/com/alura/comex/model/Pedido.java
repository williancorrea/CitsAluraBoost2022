package br.com.alura.comex.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data = LocalDate.now();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Cliente cliente;

    private BigDecimal desconto = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private TipoDesconto tipoDesconto = TipoDesconto.NENHUM;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemDePedido> itens = new ArrayList<>();

    public Pedido() {
    }

    public BigDecimal getValorPedido() {
        return BigDecimal.valueOf(itens.parallelStream().mapToDouble(a -> a.getValorTotalComDesconto().doubleValue()).sum());
    }

    public BigDecimal getValorPedidoComDesconto() {
        return getValorPedido().subtract(getValorPedido().multiply(desconto));
    }

    public BigDecimal getValorDesconto() {
        return getValorPedido().subtract(getValorPedidoComDesconto());
    }
}