package com.example.projeto1.api.produtos.usecases;

import org.springframework.stereotype.Service;

import com.example.projeto1.api.exceptions.BadRequestException;
import com.example.projeto1.api.produtos.dto.CreateProdutoRequest;
import com.example.projeto1.api.produtos.dto.ProdutoResponse;
import com.example.projeto1.api.produtos.entity.Produtos;
import com.example.projeto1.api.produtos.repository.ProdutosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateProdutoService {
    private final ProdutosRepository produtoRepository;

    public ProdutoResponse create(CreateProdutoRequest request) {
        produtoRepository.findByNome(request.getNome())
                .ifPresent(p -> {
                    throw new BadRequestException("Produto já cadastrado");
                });

        Produtos produto = new Produtos();
        produto.setNome(request.getNome());
        produto.setPreco(request.getPreco());
        produto.setQuantidadeEstoque(request.getQuantidadeEstoque());
        produto.setDescricao(request.getDescricao());
        Produtos saved = produtoRepository.save(produto);

        return ProdutoResponse.builder()
                .id(saved.getId())
                .nome(saved.getNome())
                .descricao(saved.getDescricao())
                .preco(saved.getPreco())
                .quantidadeEstoque(saved.getQuantidadeEstoque())
                .isDisabled(saved.isDisabled())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }
}
