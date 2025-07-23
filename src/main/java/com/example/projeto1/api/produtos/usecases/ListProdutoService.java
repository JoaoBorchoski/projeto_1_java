package com.example.projeto1.api.produtos.usecases;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.projeto1.api.produtos.dto.ListProdutoRequest;
import com.example.projeto1.api.produtos.entity.Produtos;
import com.example.projeto1.api.produtos.repository.ProdutoSpecification;
import com.example.projeto1.api.produtos.repository.ProdutosRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListProdutoService {

    private final ProdutosRepository produtoRepository;

    public Page<Produtos> listProdutos(ListProdutoRequest req) {
        req = Optional.ofNullable(req).orElseGet(ListProdutoRequest::new);

        int page = Optional.ofNullable(req.getPage()).orElse(0);
        int size = Optional.ofNullable(req.getSize()).orElse(10);

        Pageable pageable = PageRequest.of(page, size);
        Specification<Produtos> spec = null;

        if (req.getSearch() != null && !req.getSearch().isEmpty()) {
            String searchTerm = req.getSearch();

            spec = ProdutoSpecification.nomeContains(searchTerm);
        }
        return produtoRepository.findAll(spec, pageable);
    }
}