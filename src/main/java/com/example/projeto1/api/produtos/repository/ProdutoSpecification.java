package com.example.projeto1.api.produtos.repository;

import org.springframework.data.jpa.domain.Specification;

import com.example.projeto1.api.produtos.entity.Produtos;

public class ProdutoSpecification {

    public static Specification<Produtos> nomeContains(String termo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")),
                "%" + termo.toLowerCase() + "%");
    }

    public static Specification<Produtos> descricaoContains(String termo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")),
                "%" + termo.toLowerCase() + "%");
    }
}