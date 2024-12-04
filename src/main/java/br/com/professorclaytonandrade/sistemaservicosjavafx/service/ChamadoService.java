package br.com.professorclaytonandrade.sistemaservicosjavafx.service;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dao.ChamadoDao;
import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ChamadoDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Status;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Chamado;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Cliente;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Tecnico;

import java.util.List;


public class ChamadoService {
    private ChamadoDao chamadoDao;


    public ChamadoService() {
        this.chamadoDao = new ChamadoDao();
    }

    public List<ChamadoDto> listarTodos() {
        return chamadoDao.findAll(); // Chama o repositório para retornar todos os chamados
    }


    public void criar(ChamadoDto chamadoDto) {
        Chamado chamado = new Chamado();
        chamado.setPrioridade(chamadoDto.getPrioridade());
        chamado.setStatus(Status.ABERTO);
        chamado.setTitulo(chamadoDto.getTitulo());
        chamado.setObservacoes(chamadoDto.getObservacoes());
        chamado.setTecnico(new Tecnico(chamadoDto.getTecnicoId())); // Assumindo que o Tecnico é recuperado por ID
        chamado.setCliente(new Cliente(chamadoDto.getClienteId())); // Assumindo que o Cliente é recuperado por ID
        chamadoDao.criar(chamado);
    }

    public void atualizar(ChamadoDto chamadoDto) {
        Chamado chamado = chamadoDao.findById(chamadoDto.getId());
        chamado.setPrioridade(chamadoDto.getPrioridade());
        chamado.setStatus(chamadoDto.getStatus());
        chamado.setTitulo(chamadoDto.getTitulo());
        chamado.setObservacoes(chamadoDto.getObservacoes());

        if (chamadoDto.getStatus() == Status.ENCERRADO) {
            chamado.encerrarChamado(); // Fecha o chamado
        }

        chamadoDao.atualizar(chamado);
    }

    public void encerrarChamado(Long id) {
        Chamado chamado = chamadoDao.findById(id);
        chamado.encerrarChamado();
        chamadoDao.atualizar(chamado);
    }

}
