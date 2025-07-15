package com.example.projeto1.api.produtos.dto;

import lombok.Data;

@Data
public class PatchProdutoRequest {
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;
    private Boolean isDisabled;
}
