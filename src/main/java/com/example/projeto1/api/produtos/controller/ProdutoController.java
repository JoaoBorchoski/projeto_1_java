package com.example.projeto1.api.produtos.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto1.api.produtos.dto.CreateProdutoRequest;
import com.example.projeto1.api.produtos.dto.DeleteProdutoRequest;
import com.example.projeto1.api.produtos.dto.GetProdutoRequest;
import com.example.projeto1.api.produtos.dto.PatchProdutoRequest;
import com.example.projeto1.api.produtos.dto.ProdutoResponse;
import com.example.projeto1.api.produtos.usecases.CreateProdutoService;
import com.example.projeto1.api.produtos.usecases.DeleteProdutoService;
import com.example.projeto1.api.produtos.usecases.GetProdutoService;
import com.example.projeto1.api.produtos.usecases.PatchProdutoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Validated
public class ProdutoController {

    private final CreateProdutoService createProdutoService;
    private final GetProdutoService getProdutoService;
    private final PatchProdutoService patchProdutoService;
    private final DeleteProdutoService deleteProdutoService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> create(@Valid @RequestBody CreateProdutoRequest req) {
        ProdutoResponse produtoResponse = createProdutoService.create(req);
        return ResponseEntity
                .created(URI.create("/produtos/" + produtoResponse.getId()))
                .body(produtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> get(@PathVariable UUID id) {
        GetProdutoRequest request = new GetProdutoRequest();
        request.setId(id);
        ProdutoResponse produtoResponse = getProdutoService.getProdutoById(request);
        return ResponseEntity.ok(produtoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponse> patch(@PathVariable UUID id,
            @Valid @RequestBody PatchProdutoRequest request) {
        ProdutoResponse produtoResponse = patchProdutoService.patch(id, request);
        return ResponseEntity.ok(produtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        DeleteProdutoRequest deleteRequest = new DeleteProdutoRequest();
        deleteRequest.setId(id);
        deleteProdutoService.deleteProdutoById(deleteRequest);
        return ResponseEntity.noContent().build();
    }

}
