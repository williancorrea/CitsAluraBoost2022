package br.com.alura.comex.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class PedidoNovoDto {


    @NotNull
    private Long clienteId;

    @NotNull
    private List<ProdutosNovo> produtos = new ArrayList<>();

    @Data
    @Builder
    @AllArgsConstructor
    public static class ProdutosNovo {

        @Getter
        @NotNull
        @Positive
        private Long produtoId;

        @NotNull
        @Positive
        private Integer quantidade;
    }
}

