package com.example.projeto1.api.produtos.dto;

import lombok.Data;

@Data
public class ListProdutoRequest {
    private String search;
    private Integer page;
    private Integer size;
}
