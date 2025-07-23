CREATE TABLE IF NOT EXISTS public.produtos (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255), 
    preco NUMERIC(10, 2) NOT NULL, 
    quantidade_estoque INT NOT NULL,
    is_disabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);