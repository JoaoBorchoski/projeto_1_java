package com.example.projeto1.api.produtos.repository;

import java.util.Optional;

import com.example.projeto1.api.produtos.entity.Produtos;

public interface ProdutosRepository {
    Optional<Produtos> findByNome(String nome);
    Produtos save(Produtos produto);
}
