package com.example.projeto1.api.produtos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProdutoRequest {
    @NotBlank(message = "O nome do produto não pode ser vazio.")
    private String nome;

    @NotNull(message = "O preço do produto é obrigatório.")
    @Positive(message = "O preço do produto deve ser um valor positivo.")
    private Double preco;

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
    private Integer quantidadeEstoque;

    private String descricao;
}