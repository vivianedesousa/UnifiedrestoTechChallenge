# Unifiedresto Platform API

API REST para uma plataforma unificada de **clientes e restaurantes**, desenvolvida como parte de um *Tech Challenge*. Esta API permite cadastro, autenticação, consulta, atualização e remoção de clientes e restaurantes.

---

## 📌 Visão Geral

- **Base URL:** `http://localhost:8080/api/v1`
- **Formato:** JSON
- **Arquitetura:** REST
- **Versionamento:** `/v1`

---

## 🔐 Autenticação

Atualmente, a autenticação é realizada via **login e senha**, retornando (presumidamente) um token ou sessão (não especificado na collection).

> ⚠️ Caso a API utilize JWT ou outro mecanismo, recomenda-se documentar o header `Authorization`.

---

## 👤 Customers (Clientes)

### ➕ Cadastrar cliente

**POST** `/customers`

```json
{
  "name": "Maria",
  "cpf": "124.478.787-50",
  "email": "maria@email.com",
  "login": "marialima",
  "password": "12345677",
  "address": {
    "street": "Av Pauli",
    "number": "190",
    "city": "São Paulo",
    "postalCode": "01390-100"
  }
}
```

---

### 🔑 Login do cliente

**POST** `/customers/login`

```json
{
  "email": "maria@email.com",
  "login": "marialima",
  "password": "12345677"
}
```

---

### 📄 Listar todos os clientes

**GET** `/customers`

Retorna a lista completa de clientes cadastrados.

---

### 🔍 Buscar clientes por nome

**GET** `/customers/search?name=Maria`

Parâmetros de query:
- `name` (string): Nome ou parte do nome do cliente

---

### ✏️ Atualizar cliente pelo ID

**PUT** `/customers/{id}`

```json
{
  "name": "Rafaela Atualizada",
  "email": "rafaela@email.com",
  "login": "raquel"
}
```

---

### 🔒 Alterar senha do cliente

**PATCH** `/customers/{id}/password`

```json
{
  "currentPassword": "password4578",
  "password": "novaSenha1237777777"
}
```

---

### 🗑️ Remover cliente pelo ID

**DELETE** `/customers/{id}`

Remove permanentemente o cliente.

---

## 🍽️ Restaurants (Restaurantes)

### ➕ Cadastrar restaurante

**POST** `/restaurants`

```json
{
  "name": "Vivian Taste Restaurant",
  "cnpj": "12.349.678/0001-78",
  "email": "vivia@hometasterestaurant.com",
  "login": "vivnerestaurant",
  "password": "viviane@@9",
  "address": {
    "street": "Av Flores",
    "number": "1056",
    "city": "Vitória - ES",
    "postalCode": "01367-190"
  }
}
```

---

### 🔑 Login do restaurante

**POST** `/restaurants/login`

```json
{
  "login": "hometasterestaurant",
  "password": "ana35678"
}
```

---

### 📄 Listar todos os restaurantes

**GET** `/restaurants`

Retorna todos os restaurantes cadastrados.

---

### 🔍 Buscar restaurante por nome

**GET** `/restaurants/search?name=Golden`

Parâmetros de query:
- `name` (string): Nome ou parte do nome do restaurante

---

### ✏️ Atualizar restaurante pelo ID

**PUT** `/restaurants/{id}`

```json
{
  "name": "Golden Fork Bistro Atualizado",
  "email": "cont@goldenforkbistro.com",
  "login": "goldenfork",
  "address": {
    "street": "Rua Nova",
    "number": "200",
    "city": "São Paulo",
    "postalCode": "01000-000"
  }
}
```

---

### 🔒 Alterar senha do restaurante

**PATCH** `/restaurants/{id}/password`

```json
{
  "currentPassword": "123456",
  "password": "SenhaNova@2029"
}
```

---

## 🧪 Testes

Esta API possui uma **Postman Collection** para testes manuais:
- Collection: `UnifiedrestoPlatformTechChallenge.postman_collection.json`

---

## 🚀 Executando o projeto

```bash
# exemplo genérico
mvn spring-boot:run
```

API disponível em:
```
http://localhost:8080/api/v1
```

---

## 📎 Observações Técnicas

- Endpoints seguem padrão REST
- Versionamento por URL
- Estrutura pronta para integração com API Gateway
- Ideal para uso em ambientes Docker/Kubernetes

---

## ✍️ Autor Viviane de Sousa Lima

Projeto desenvolvido para fins de estudo e avaliação técnica.

---

Se desejar, este README pode ser facilmente convertido para **Swagger / OpenAPI**, ou