package br.com.alura.comex.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categorias")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 60)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCategoria status = StatusCategoria.ATIVA;

    @OneToMany(mappedBy = "categoria")
    @ToString.Exclude
    private List<Produto> produtos = new ArrayList<>();

    public Categoria() {
    }

    public void adicionaProduto(Produto produto) {
        produto.setCategoria(this);
        this.produtos.add(produto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Categoria categoria = (Categoria) o;
        return id != null && Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}