package br.com.alura.comex.model.dto;

import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.model.StatusCategoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
public class CategoriaDto {

    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 60)
    private String nome;

    @NotNull
    private StatusCategoria status;

    private List<ProdutoDto> produtos = new ArrayList<>();

    public CategoriaDto() {
    }

    public CategoriaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.status = categoria.getStatus();
        this.produtos = categoria.getProdutos().parallelStream().map(ProdutoDto::new).collect(Collectors.toList());
    }

    public Categoria convert() {
        return Categoria.builder()
                .id(this.id)
                .nome(this.nome)
                .status(this.status)
                .produtos(this.convert().getProdutos())
                .build();
    }
}