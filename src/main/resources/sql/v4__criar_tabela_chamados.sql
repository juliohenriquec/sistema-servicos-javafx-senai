CREATE TABLE chamado (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         prioridade VARCHAR(20) NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         titulo VARCHAR(255) NOT NULL,
                         observacoes TEXT,
                         tecnico_id BIGINT,
                         cliente_id BIGINT,
                         data_abertura DATE NOT NULL,
                         data_fechamento DATE,
                         FOREIGN KEY (tecnico_id) REFERENCES tecnico(id),
                         FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

