package br.com.alura.comex.model.dto;

import br.com.alura.comex.model.Cliente;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ClienteDto {

    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 60)
    private String nome;

    @NotNull
    @NotEmpty
    @Length(min = 11, max = 14)
    private String cpf;

    @Length(max = 100)
    private String telefone;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 100)
    private String rua;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 20)
    private String numero;

    @Length(max = 100)
    private String complemento;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 100)
    private String bairro;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 100)
    private String cidade;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 50)
    private String estado;


    public ClienteDto() {
    }

    public ClienteDto(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.rua = cliente.getRua();
        this.numero = cliente.getNumero();
        this.complemento = cliente.getComplemento();
        this.bairro = cliente.getBairro();
        this.cidade = cliente.getCidade();
        this.estado = cliente.getEstado();
    }

    public Cliente convert() {
        return Cliente.builder()
                .id(this.id)
                .nome(this.nome)
                .cpf(this.cpf)
                .telefone(this.telefone)
                .rua(this.rua)
                .numero(this.numero)
                .complemento(this.complemento)
                .bairro(this.bairro)
                .cidade(this.cidade)
                .estado(this.estado)
                .build();
    }
}