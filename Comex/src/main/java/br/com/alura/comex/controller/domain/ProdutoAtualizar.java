package br.com.alura.comex.controller.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoAtualizar {

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @Positive
    private BigDecimal preco;

    private String descricao;

    @NotNull
    private Integer qtdEstoque;

    @NotNull
    @Positive
    private Long categoriaId;
}
