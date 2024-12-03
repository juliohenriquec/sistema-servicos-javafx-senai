package br.com.professorclaytonandrade.sistemaservicosjavafx.model;

import java.time.LocalDate;

public abstract class Pessoa {
    protected Long id;
    protected String nome;
    protected String email;
    protected String senha;
    protected String cpf;
    protected LocalDate dataCriacao = LocalDate.now();

    public Pessoa(){

    }

    public Pessoa(Long id, String nome, String email, String senha, String cpf, LocalDate dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
