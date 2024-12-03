package br.com.clayton.sistemadeservicos.model;

import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Prioridade;
import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Status;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Cliente;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Tecnico;

import java.time.LocalDate;

public class Chamado {

    private Long id;
    private Prioridade prioridade;
    private Status status;
    private String titulo;
    private String observacoes;
    private Tecnico tecnico;
    private Cliente cliente;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;

    public Chamado() {
        this.dataAbertura = LocalDate.now();
        this.status = Status.ABERTO;  // Inicia sempre como "Aberto"
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    // Método para encerrar o chamado
    public void encerrarChamado() {
        this.status = Status.ENCERRADO;
        this.dataFechamento = LocalDate.now();
    }
}
