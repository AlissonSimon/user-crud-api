# User API - CRUD & Unit Testing

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)

Este repositório contém uma API RESTful completa para gerenciamento de usuários. O objetivo principal do projeto é demonstrar a implementação de um CRUD completo (Create, Read, Update, Delete) seguindo as melhores práticas do ecossistema Spring, com foco na qualidade de software através de **Testes Unitários de Endpoints**.

## 💻 Tecnologias e Ferramentas Utilizadas

* **Java**
* **Spring Boot** (Web, Data JPA)
* **H2 Database** (Banco de dados em memória para facilitar os testes e a execução local)
* **JUnit 5 & Mockito** para a criação de testes unitários isolados e eficientes
* **Lombok** para redução de código boilerplate
* **SpringDoc OpenAPI (Swagger)** para documentação interativa
* **Maven** para gerenciamento de dependências

## ⚙️ Funcionalidades e Endpoints

A API expõe os seguintes endpoints para o gerenciamento da entidade `User`:

* **Criar Usuário (`POST /users`):** Recebe os dados e cadastra um novo usuário.
* **Buscar Todos (`GET /users`):** Retorna uma lista com todos os usuários cadastrados (`findAll`).
* **Buscar por ID (`GET /users/{id}`):** Retorna os detalhes de um usuário específico baseado no seu ID (`findById`).
* **Atualizar Usuário (`PUT /users/{id}`):** Atualiza os dados de um usuário existente.
* **Deletar Usuário (`DELETE /users/{id}`):** Remove um usuário do sistema.

### 🧪 Cobertura de Testes
Todos os controllers e regras de negócio foram validados com testes unitários garantindo que os endpoints retornem os HTTP Status corretos (como `200 OK`, `201 Created`, `404 Not Found`, etc.) e os payloads esperados em cenários de sucesso e de erro.

## 🚀 Como Executar o Projeto

### Pré-requisitos
* Java 
* Maven

### Passos

1. Clone este repositório:
   
```bash
git clone [https://github.com/AlissonSimon/user-crud-api.git](https://github.com/AlissonSimon/user-crud-api.git)
```
2. Acesse a pasta do projeto:

```bash
cd user-crud-api
```
(Opcional) Execute a suíte de testes unitários:

```bash
mvn test
```
