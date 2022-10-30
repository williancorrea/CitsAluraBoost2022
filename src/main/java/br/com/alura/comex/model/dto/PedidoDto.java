package br.com.alura.comex.model.dto;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.model.TipoDesconto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
public class PedidoDto {

    private Long id;
    private LocalDate data = LocalDate.now();
    private Long clienteId;
    private BigDecimal desconto;

    public PedidoDto() {
    }

    public PedidoDto(Pedido pedido) {
        this.id = pedido.getId();
        this.data = pedido.getData();
        this.clienteId = pedido.getCliente().getId();
        this.desconto = pedido.getDesconto();
    }

    public Pedido convert() {
        return Pedido.builder()
                .id(this.id)
                .data(this.data)
                .cliente(Cliente.builder()
                        .id(this.clienteId)
                        .build())
                .desconto(this.desconto)
                .build();
    }
}