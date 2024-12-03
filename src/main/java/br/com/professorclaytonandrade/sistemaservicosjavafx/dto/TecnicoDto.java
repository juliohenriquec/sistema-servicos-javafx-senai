package br.com.professorclaytonandrade.sistemaservicosjavafx.dto;

import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Tecnico;

import java.time.LocalDate;

public class TecnicoDto {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private Double salario;
    private LocalDate dataCriacao;


    public TecnicoDto(Long id, String nome, String email, String senha, String cpf, Double salario, LocalDate dataCriacao) {
        validarCampos(nome, email, senha, cpf, salario);
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.salario = salario;
        this.dataCriacao = dataCriacao;
    }

    public TecnicoDto(Tecnico tecnico) {
        validarCampos(tecnico.getNome(), tecnico.getEmail(), tecnico.getSenha(), tecnico.getCpf(), tecnico.getSalario());
        this.id = tecnico.getId();
        this.nome = tecnico.getNome();
        this.email = tecnico.getEmail();
        this.senha = tecnico.getSenha();
        this.cpf = tecnico.getCpf();
        this.salario = tecnico.getSalario();
        this.dataCriacao = tecnico.getDataCriacao();
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
        validarNome(nome);
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validarEmail(email);
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        validarSenha(senha);
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        validarCpf(cpf);
        this.cpf = cpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        } else if (nome.length() < 3) {
            throw new IllegalArgumentException("O nome deve conter pelo menos 3 caracteres");
        }
    }

    private static void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail não pode ser vazio");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("E-mail inválido");
        }
    }

    private static void validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia");
        } else if (senha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres");
        }
    }

    private static void validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser vazio");
        } else if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos numéricos");
        }
    }

    private static void validarSalario(Double salario) {
        if (salario == null) {
            throw new IllegalArgumentException("O salário não pode ser nulo");
        } else if (salario < 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero");
        }
    }

    private static void validarCampos(String nome, String email, String senha, String cpf, Double salario) {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarCpf(cpf);
        validarSalario(salario);
    }
}
