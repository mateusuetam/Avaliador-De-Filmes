# Avaliador de Filmes

Um pequeno sistema web experimental desenvolvido com **Spring Boot**, **Thymeleaf**, **jQuery**, **Spring Data JPA** e **MySQL** para cadastro, gerenciamento e análise de filmes.

## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* Thymeleaf
* jQuery
* MySQL
* Maven

## Funcionalidades

* Cadastrar filmes
* Listar filmes
* Editar filmes
* Excluir filmes
* Cadastrar análises para os filmes
* Listar análises
* Editar análises
* Excluir análises
* Consumir a API REST pelo front-end utilizando jQuery
* Alternar entre tema claro e tema escuro
* Persistir a preferência de tema utilizando cookies

## Banco de dados

O projeto utiliza um banco de dados MySQL chamado `avaliador_filmes` e antes de executar a aplicação, é necessário criar o banco de dados com o script `dbscript.sql` (as tabelas necessárias serão criadas e atualizadas automaticamente pelo Hibernate).

## Configuração das credenciais

As credenciais do MySQL não estão armazenadas diretamente no projeto. O arquivo `application.properties` utiliza das variáveis:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```
Portanto, é necessário informar o usuário e a senha do banco de dados ao executar a aplicação para a autenticação.

## Compilando o projeto

Como a pasta `target/` não é versionada no GitHub, após clonar o repositório é necessário compilar o projeto para gerar os arquivos de build. O projeto já possui o Maven Wrapper (`mvnw`), portanto não é necessário instalar o Maven separadamente. Basta definir as credenciais do MySQL e compilar o projeto em um único comando:

```bash
DB_USERNAME=root DB_PASSWORD=SUA_SENHA_DO_BD ./mvnw clean package
```

Durante o processo de compilação, os testes da aplicação são executados. Por isso, o MySQL deve estar em execução e o banco `avaliador_filmes` deve existir. Ao final de uma compilação bem-sucedida, o Maven exibirá:

```text
BUILD SUCCESS
```

e a pasta `target/` será criada automaticamente.
