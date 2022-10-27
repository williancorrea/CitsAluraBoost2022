package br.com.alura.comex.model.dto;

import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Produto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Builder
public class ProdutoDto {

    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 60)
    private String nome;

    @Length(max = 100)
    private String descricao;
    private BigDecimal precoUnitario;
    private int quantidadeEstoque;


    //TODO: Verificar e remover
    private Long categoriaId;

    public ProdutoDto() {
    }

    public ProdutoDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.precoUnitario = produto.getPrecoUnitario();
        this.categoriaId = produto.getCategoria().getId();
    }

    public Produto convert() {
        return Produto.builder()
                .id(this.id)
                .nome(this.nome)
                .descricao(this.descricao)
                .precoUnitario(this.precoUnitario)
                .categoria(Categoria.builder()
                        .id(this.categoriaId)
                        .build())
                .build();
    }
}