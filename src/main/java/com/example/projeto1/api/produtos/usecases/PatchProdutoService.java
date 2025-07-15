package com.example.projeto1.api.produtos.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.projeto1.api.exceptions.ResourceNotFoundException;
import com.example.projeto1.api.produtos.dto.PatchProdutoRequest;
import com.example.projeto1.api.produtos.dto.ProdutoResponse;
import com.example.projeto1.api.produtos.entity.Produtos;
import com.example.projeto1.api.produtos.repository.ProdutosRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatchProdutoService {
    private final ProdutosRepository produtosRepository;

    public ProdutoResponse patch(UUID id, PatchProdutoRequest request) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (request.getNome() != null) {
            produto.setNome(request.getNome());
        }
        if (request.getDescricao() != null) {
            produto.setDescricao(request.getDescricao());
        }
        if (request.getPreco() != null) {
            produto.setPreco(request.getPreco());
        }
        if (request.getQuantidadeEstoque() != null) {
            produto.setQuantidadeEstoque(request.getQuantidadeEstoque());
        }
        if (request.getIsDisabled() != null) {
            produto.setDisabled(request.getIsDisabled());
        }

        Produtos updatedProduto = produtosRepository.save(produto);

        return ProdutoResponse.builder()
                .id(updatedProduto.getId())
                .nome(updatedProduto.getNome())
                .descricao(updatedProduto.getDescricao())
                .preco(updatedProduto.getPreco())
                .quantidadeEstoque(updatedProduto.getQuantidadeEstoque())
                .isDisabled(updatedProduto.isDisabled())
                .createdAt(updatedProduto.getCreatedAt())
                .updatedAt(updatedProduto.getUpdatedAt())
                .build();
    }
}
