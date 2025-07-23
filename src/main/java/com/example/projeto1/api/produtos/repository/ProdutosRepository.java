package com.example.projeto1.api.produtos.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.projeto1.api.produtos.entity.Produtos;

public interface ProdutosRepository extends JpaRepository<Produtos, UUID>, JpaSpecificationExecutor<Produtos> {
    Optional<Produtos> findByNome(String nome);
}
