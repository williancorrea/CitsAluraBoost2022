package br.com.alura.comex.model.dto;

import br.com.alura.comex.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class ClienteListDto {

    private String nome;

    private String cpf;

    private String telefone;

    private String local;


    public ClienteListDto() {
    }

    public ClienteListDto(Cliente cliente) {
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.local = cliente.getCidade() + "/" + cliente.getEstado();
    }
}