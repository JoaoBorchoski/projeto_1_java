# Projeto-1 - API com Spring Boot (Autenticação com JWT)

Este projeto foi criado com o objetivo de aprofundar meus conhecimentos em desenvolvimento de APIs utilizando **Java** e a stack **Spring**.

## Módulos Existentes

### 1. **Autenticação com JWT**
O módulo de **autenticação com JWT** foi implementado para permitir que os usuários façam login na aplicação e recebam um **token JWT**. Este token pode ser utilizado para autenticação em futuras requisições à API, garantindo que o acesso a endpoints protegidos seja feito por usuários autenticados.

**Detalhes do processo de autenticação:**
- **Endpoint `/login`**: O usuário envia suas credenciais (**login** e **senha**) no corpo da requisição.
- O sistema verifica se o **login** existe no banco de dados e compara a **senha** fornecida com a versão hash armazenada usando o algoritmo **bcrypt**.
- Se as credenciais forem válidas, um **token JWT** é gerado e retornado ao usuário.
- O token JWT contém informações sobre o usuário (login) e tem um tempo de expiração configurável.

Esse módulo é a base para futuras implementações de controle de acesso a outros endpoints da API.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot** (versão mais recente)
- **Spring Security** (para controle de segurança e autenticação)
- **JWT (JSON Web Token)** (para autenticação sem estado)
- **PostgreSQL** (para persistência de dados)
- **bcrypt** (para hash de senhas)
- **JPA** (para interação com o banco de dados)

## Estrutura do Projeto

A estrutura do projeto segue as boas práticas de **Clean Architecture**, com camadas separadas para manter a organização e a escalabilidade da aplicação:

- **API**: Camada responsável por expor os endpoints da API (Controller, Request, Response).
- **Application**: Camada que contém a lógica de negócio (Serviços de autenticação, etc.).
- **Domain**: Camada que contém as entidades e interfaces de repositórios.
- **Infrastructure**: Camada que lida com a implementação concreta dos repositórios, segurança e interações com o banco de dados.
