package br.com.professorclaytonandrade.sistemaservicosjavafx.dto;

import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Prioridade;
import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Status;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Chamado;

import java.time.LocalDate;

public class ChamadoDto {

    private Long id;
    private Prioridade prioridade;
    private Status status;
    private String titulo;
    private String observacoes;
    private Long tecnicoId;
    private Long clienteId;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;



    public ChamadoDto(Long id, Prioridade prioridade, Status status, String titulo, String observacoes,
                      Long tecnicoId, Long clienteId, LocalDate dataAbertura, LocalDate dataFechamento) {
        this.id = id;
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnicoId = tecnicoId;
        this.clienteId = clienteId;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
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

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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

    // Métodos de conversão

    /**
     * Converte um objeto `Chamado` para um objeto `ChamadoDto`.
     */
    public static ChamadoDto fromChamado(Chamado chamado) {
        ChamadoDto dto = new ChamadoDto(
                chamado.getId(),
                chamado.getPrioridade(),
                chamado.getStatus(),
                chamado.getTitulo(),
                chamado.getObservacoes(),
                chamado.getTecnico() != null ? chamado.getTecnico().getId() : null,
                chamado.getCliente() != null ? chamado.getCliente().getId() : null,
                chamado.getDataAbertura(),
                chamado.getDataFechamento()
        );
        return dto;
    }

    /**
     * Converte um objeto `ChamadoDto` para um objeto `Chamado`.
     */
    public static Chamado toChamado(ChamadoDto dto) {
        Chamado chamado = new Chamado();
        chamado.setId(dto.getId());
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setStatus(dto.getStatus());
        chamado.setTitulo(dto.getTitulo());
        chamado.setObservacoes(dto.getObservacoes());
        chamado.setDataAbertura(dto.getDataAbertura());
        chamado.setDataFechamento(dto.getDataFechamento());
        return chamado;
    }


}
