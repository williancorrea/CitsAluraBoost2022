package br.com.alura.comex.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginForm {
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String senha;
}
