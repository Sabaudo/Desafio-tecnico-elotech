# Desafio-tecnico-elotech
## Aplicação RESTful de cadastro de pessoas com a utilização de Java + Angular

Tech stack e versões:
- Java 17
- Spring 3.2.5
- MySQL
- Angular 17.3.5
- Node 20.11.1
- Npm 10.5.2

### Informações
- As configurações de banco estão descritas no `application.properties`
- Para rodar o backend, basta rodar o arquivo de inicialização localizado em `teste-tecnico-back-end/elotech/src/main/java/br/com/teste/elotech/ElotechApplication.java`
- Para rodar o frontend, acesse a pasta `teste-tecnico-front-end` e use o comando ng serve (será inicializado em http://localhost:4200/)

### APIs

- GET - `/pessoa?nome=` - Busca uma lista de pessoas (pode-se utilizar filtro de nome)
- GET - `/pessoa/id` - Busca uma pessoa com o respectivo `id`
- POST - `/pessoa` - Cadastra uma pessoa
- PUT - `/pessoa/id` - Atualiza um cadastro de pessoa com o respectivo `id`
- DELETE - `/pessoa/id` - Delete uma pessoa com o respectivo `id`





