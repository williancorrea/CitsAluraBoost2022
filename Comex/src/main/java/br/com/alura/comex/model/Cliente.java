package br.com.alura.comex.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 60)
    @Column(nullable = false)
    private String nome;

    @NotNull
    @NotEmpty
    @Length(min = 11, max = 14)
    @CPF
    @Column(nullable = false)
    private String cpf;

    @Length(max = 100)
    private String telefone;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 100)
    @Column(nullable = false)
    private String rua;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 20)
    @Column(nullable = false)
    private String numero;

    @Length(max = 100)
    private String complemento;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 100)
    @Column(nullable = false)
    private String bairro;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 100)
    @Column(nullable = false)
    private String cidade;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 50)
    @Column(nullable = false)
    private String estado;

    @OneToOne
    private Usuario usuario;

    public Cliente() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cliente cliente = (Cliente) o;
        return id != null && Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}