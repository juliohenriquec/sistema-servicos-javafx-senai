package br.com.professorclaytonandrade.sistemaservicosjavafx.model;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.TecnicoDto;

import java.time.LocalDate;

public class Tecnico extends Pessoa{
    private Double salario;

    public Tecnico(){
        super();
    }

    public Tecnico(Long id, String nome, String email, String senha, String cpf, Double salario, LocalDate dataCriacao) {
        super(id, nome, email, senha, cpf, dataCriacao);
        this.salario = salario;
    }

    public Tecnico(TecnicoDto tecnicoDto) {
        super(
            (tecnicoDto.getId() != null) ? tecnicoDto.getId() : null,
            tecnicoDto.getNome(),
            tecnicoDto.getEmail(),
            tecnicoDto.getSenha(),
            tecnicoDto.getCpf(),
            tecnicoDto.getDataCriacao()
        );
        this.salario = tecnicoDto.getSalario();
    }

    public Tecnico(Long id) {
        this.id = id;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
}