package br.com.professorclaytonandrade.sistemaservicosjavafx.model;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ClienteDto;

import java.time.LocalDate;

public class Cliente extends Pessoa {
    private String telefone;

    public Cliente() {
        super();
    }

    public Cliente(Long id, String nome, String email, String senha, String cpf, String telefone, LocalDate dataCriacao) {
        super(id, nome, email, senha, cpf, dataCriacao);
        this.telefone = telefone;
    }

    public Cliente(ClienteDto clienteDto) {
        super(
                (clienteDto.getId() != null) ? clienteDto.getId() : null,
                clienteDto.getNome(),
                clienteDto.getEmail(),
                clienteDto.getSenha(),
                clienteDto.getCpf(),
                clienteDto.getDataCriacao()
        );
        this.telefone = clienteDto.getTelefone();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

