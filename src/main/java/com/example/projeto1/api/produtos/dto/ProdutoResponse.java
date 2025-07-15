package com.example.projeto1.api.produtos.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoResponse {
    private UUID id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;
    private boolean isDisabled;
    private Instant createdAt;
    private Instant updatedAt;
}
