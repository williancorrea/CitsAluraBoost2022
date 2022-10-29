package br.com.alura.comex.model.dto;

import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
public class ProdutoDto {

    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 60)
    private String nome;

    @Length(max = 100)
    private String descricao;
    @NotNull
    @Positive
    private BigDecimal precoUnitario;

    @NotNull
    private Integer quantidadeEstoque;
    @NotNull
    private Long categoriaId;

    public ProdutoDto() {
    }

    public ProdutoDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.precoUnitario = produto.getPrecoUnitario();
        this.categoriaId = produto.getCategoria().getId();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
    }

    public Produto convert() {
        return Produto.builder()
                .id(this.id)
                .nome(this.nome)
                .descricao(this.descricao)
                .precoUnitario(this.precoUnitario)
                .quantidadeEstoque(this.quantidadeEstoque)
                .categoria(Categoria.builder()
                        .id(this.categoriaId)
                        .build())
                .build();
    }
}