package br.com.alura.comex.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "perfil")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Perfil implements GrantedAuthority {

    private static final long serialVersionUID = 6122908909108604099L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 60)
    private String nome;

    public Perfil() {
    }

    @Override
    public String getAuthority() {
        return this.nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return id.equals(perfil.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
