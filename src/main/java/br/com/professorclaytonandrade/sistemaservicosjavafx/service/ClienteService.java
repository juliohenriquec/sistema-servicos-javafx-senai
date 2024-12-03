package br.com.professorclaytonandrade.sistemaservicosjavafx.service;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dao.ClienteDao;
import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ClienteDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteDao clienteDao;

    public ClienteService() {
        this.clienteDao = new ClienteDao();
    }

    // Método para criar um novo cliente
    public boolean criar(ClienteDto clienteDto) {
        Cliente cliente = new Cliente(clienteDto);
        return clienteDao.inserir(cliente);
    }

    // Método para atualizar um cliente existente
    public boolean atualizar(ClienteDto clienteDto) {
        Cliente cliente = new Cliente(clienteDto);
        return clienteDao.atualizar(cliente);
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarPorId(Long id) {
        Cliente cliente = clienteDao.buscarPorId(id);
        return cliente;
    }

    // Método para remover um cliente pelo ID
    public void remover(Long id) {
        clienteDao.excluir(id);
    }

    // Método para listar todos os clientes como DTOs
    public List<ClienteDto> listarTodos() {
        List<Cliente> clientes = clienteDao.listar(); // Busca a lista de Cliente
        return clientes.stream()
                .map(cliente -> new ClienteDto(cliente)) // Mapeia cada Cliente para um ClienteDto
                .collect(Collectors.toList()); // Coleta o resultado em uma lista
    }
}
