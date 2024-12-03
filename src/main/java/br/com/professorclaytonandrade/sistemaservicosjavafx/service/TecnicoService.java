package br.com.professorclaytonandrade.sistemaservicosjavafx.service;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dao.TecnicoDao;
import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.TecnicoDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Tecnico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TecnicoService {

    private static final Logger logger = LoggerFactory.getLogger(TecnicoService.class);
    private final TecnicoDao tecnicoDao;

    public TecnicoService() {
        this.tecnicoDao = new TecnicoDao();
    }

    public boolean criar(TecnicoDto tecnicoDto) {
        Tecnico tecnico = new Tecnico(tecnicoDto);
        return tecnicoDao.inserir(tecnico);
    }

    public boolean atualizar(TecnicoDto tecnicoDto) {
        Tecnico tecnico = new Tecnico(tecnicoDto);
        return tecnicoDao.atualizar(tecnico);
    }

    public Tecnico buscarPorId(Long id) {
        Tecnico tecnico = tecnicoDao.buscarPorId(id);
        return tecnico;
    }

    public void remover(Long id) {
        tecnicoDao.excluir(id);
    }

    public List<TecnicoDto> listarTodos() {
        List<Tecnico> tecnicos = tecnicoDao.listar();  // Busca a lista de Tecnico
        return tecnicos.stream()
                .map(tecnico -> new TecnicoDto(tecnico))  // Mapeia cada Tecnico para um TecnicoDto
                .collect(Collectors.toList());  // Coleta o resultado em uma lista
    }

}
