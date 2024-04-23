CREATE TABLE contato (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    nome varchar(70) NOT NULL,
    telefone varchar(20) NOT NULL,
    email varchar(70) NOT NULL,
    pessoa_id bigint NOT NULL,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);