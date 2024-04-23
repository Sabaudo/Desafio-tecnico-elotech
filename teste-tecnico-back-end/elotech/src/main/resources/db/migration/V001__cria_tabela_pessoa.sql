CREATE TABLE pessoa(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    nome varchar(70) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL
);