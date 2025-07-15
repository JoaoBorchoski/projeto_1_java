package com.example.projeto1.api.produtos.usecases;

import org.springframework.stereotype.Service;

import com.example.projeto1.api.exceptions.ResourceNotFoundException;
import com.example.projeto1.api.produtos.dto.DeleteProdutoRequest;
import com.example.projeto1.api.produtos.entity.Produtos;
import com.example.projeto1.api.produtos.repository.ProdutosRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteProdutoService {
    private final ProdutosRepository produtoRepository;

    public void deleteProdutoById(DeleteProdutoRequest req) {
        Produtos produto = produtoRepository.findById(req.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + req.getId()));

        produtoRepository.delete(produto);
    }
}
