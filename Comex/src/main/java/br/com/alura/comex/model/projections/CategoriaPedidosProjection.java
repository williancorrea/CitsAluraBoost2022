package br.com.alura.comex.model.projections;

import java.math.BigDecimal;

public interface CategoriaPedidosProjection {

    String getNome();
    Integer getQuantidade();
    BigDecimal getMontante();
}
