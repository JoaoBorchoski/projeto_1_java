package com.example.projeto1.api.produtos.usecases;

import org.springframework.stereotype.Service;

import com.example.projeto1.api.exceptions.ResourceNotFoundException;
import com.example.projeto1.api.produtos.dto.GetProdutoRequest;
import com.example.projeto1.api.produtos.dto.ProdutoResponse;
import com.example.projeto1.api.produtos.entity.Produtos;
import com.example.projeto1.api.produtos.repository.ProdutosRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetProdutoService {

    private final ProdutosRepository produtoRepository;

    public ProdutoResponse getProdutoById(GetProdutoRequest req) {
        Produtos produto = produtoRepository.findById(req.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + req.getId()));

        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .quantidadeEstoque(produto.getQuantidadeEstoque())
                .isDisabled(produto.isDisabled())
                .createdAt(produto.getCreatedAt())
                .updatedAt(produto.getUpdatedAt())
                .build();
    }

}
