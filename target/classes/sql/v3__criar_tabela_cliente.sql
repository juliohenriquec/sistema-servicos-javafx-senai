CREATE TABLE cliente (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL,
                         senha VARCHAR(100) NOT NULL,
                         cpf VARCHAR(11) NOT NULL UNIQUE,
                         telefone VARCHAR(15),
                         data_criacao DATE NOT NULL
);
