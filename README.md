# Projeto: [ Unifiedresto Platform ]

Equipe: [ Equipe numero 34 ]

---

## 1. Introdução

### Descrição do problema

O projeto [ Unifiedresto Platform  ] consiste no desenvolvimento de um backend para gestão de usuários em um ecossistema de restaurantes. O sistema contempla dois perfis distintos de usuários: clientes e donos de restaurante, garantindo regras específicas de cadastro, autenticação, atualização de dados e segurança.

A aplicação foi construída seguindo boas práticas de arquitetura, padronização de erros e versionamento de API, atendendo integralmente aos requisitos propostos no desafio.

### Objetivo do projeto

Desenvolver um backend robusto utilizando Spring Boot, com persistência em banco de dados relacional, documentação via Swagger/OpenAPI, execução com Docker Compose e testes automatizados.

---

## 2. Arquitetura do Sistema

### Descrição da Arquitetura

A aplicação segue o padrão de arquitetura em camadas:

* Controller: Responsável por expor os endpoints REST e receber as requisições HTTP.
* Service: Contém as regras de negócio e validações da aplicação.
* Repository: Camada de acesso a dados utilizando Spring Data JPA.
* Model (Entity): Representa as entidades persistidas no banco de dados.
* DTO (Data Transfer Object): Responsável por padronizar a entrada e saída de dados da API.
* *Exception / Handler: Implementa o padrão *ProblemDetail (RFC 7807) para respostas de erro padronizadas.

O sistema utiliza API versionada (/api/v1) e banco de dados relacional (PostgreSQL ou MySQL), executado via Docker Compose.

### Diagrama da Arquitetura (Descrição)


Controller -> Service -> Repository -> Database
|
-> DTO / Validation / Exception Handling


---

## 3. Modelagem das Entidades

### Address

* address_id (PK)
* street
* number
* city
* postal_code

### Customer

* customer_id (PK)
* name
* cpf (único)
* email (único)
* login (único)
* password
* last_update
* address_id (FK – relacionamento OneToOne)

### Restaurant

* restaurant_id (PK)
* name
* cnpj (único)
* email (único)
* login (único)
* password
* last_update
* address_id (FK – relacionamento OneToOne)

---

## 4. Descrição dos Endpoints da API

### Customers (/api/v1/customers)

| Endpoint       | Método | Descrição                        |
| -------------- | ------ | -------------------------------- |
| /              | POST   | Cadastro de cliente              |
| /{id}          | PUT    | Atualização de dados (sem senha) |
| /login         | POST   | Login do cliente                 |
| /search?name=  | GET    | Busca de clientes por nome       |
| /              | GET    | Listagem completa de clientes    |
| /{id}/password | PATCH  | Alteração de senha               |
| /{id}          | DELETE | Remoção de cliente               |

### Restaurants (/api/v1/restaurants)

| Endpoint       | Método | Descrição                        |
| -------------- | ------ | -------------------------------- |
| /              | POST   | Cadastro de restaurante          |
| /{id}          | PUT    | Atualização de dados (sem senha) |
| /login         | POST   | Login do restaurante             |
| /search?name=  | GET    | Busca por nome                   |
| /              | GET    | Listagem completa                |
| /{id}/password | PATCH  | Alteração de senha               |
| /{id}          | DELETE | Remoção de restaurante           |

---

## 5. Tratamento de Erros (ProblemDetail – RFC 7807)

A aplicação utiliza ProblemDetail para padronizar as respostas de erro.

Exemplo de erro 409 (E-mail duplicado):

json
{
"type": "https://unifiedresto/errors/email-already-exists",
"title": "Email is already registered",
"status": 409,
"detail": "Email already exists",
"instance": "/api/v1/customers"
}


Erros tratados:

* 400 – Dados inválidos
* 401 – Login ou senha inválidos
* 404 – Recurso não encontrado
* 409 – Conflitos (email, login, CPF ou CNPJ duplicados)

---

## 6. Documentação Swagger

A API está documentada utilizando Swagger/OpenAPI, permitindo:

* Visualização de todos os endpoints
* Testes diretos pela interface
* Exemplos de requisição e resposta

A documentação pode ser acessada após a execução da aplicação em:


http://localhost:8080/swagger-ui.html


---

## 7. Collections para Testes (Postman)

A collection do Postman contempla os principais cenários:

* Cadastro válido
* Cadastro inválido (email, login, CPF/CNPJ duplicados)
* Login válido e inválido
* Atualização de dados
* Troca de senha (endpoint exclusivo)
* Busca por nome

A collection está disponível no repositório do projeto em formato JSON.

---

## 8. Banco de Dados

Banco relacional utilizando PostgreSQL ou MySQL.

Tabelas principais:

* address
* customer
* restaurant

Relacionamentos:

* Customer → Address (OneToOne)
* Restaurant → Address (OneToOne)

---

## 9. Execução com Docker Compose

### Passo a passo


1. Subir os containers:


docker-compose up -d


3. Acessar a aplicação:


http://localhost:8080


O docker-compose.yml orquestra a aplicação Spring Boot e o banco de dados, incluindo variáveis de ambiente para conexão.

---

## 10. Qualidade do Código

* Uso de boas práticas do Spring Boot
* Separação clara de responsabilidades
* Aplicação dos princípios SOLID
* Código organizado e testável
* Testes unitários com JUnit e Mockito

---

## 12. Considerações Finais

O projeto atende a todos os requisitos propostos, apresentando uma arquitetura sólida, código limpo, documentação completa e tratamento de erros padronizado, estando apto para avaliação acadêmica e futura evolução.