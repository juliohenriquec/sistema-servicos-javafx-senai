package br.com.professorclaytonandrade.sistemaservicosjavafx.dao;

import br.com.professorclaytonandrade.sistemaservicosjavafx.config.conexao.FabricaDeConexao;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    private static final Logger logger = LoggerFactory.getLogger(ClienteDao.class);

    // Método para inserir um cliente no banco de dados
    public boolean inserir(Cliente cliente) {
        boolean isInserido = false;
        cliente.setId(null);
        String sql = "INSERT INTO cliente (nome, email, senha, cpf, telefone, data_criacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getSenha());
            statement.setString(4, cliente.getCpf());
            statement.setString(5, cliente.getTelefone());
            statement.setDate(6, Date.valueOf(cliente.getDataCriacao()));

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                isInserido = true;
                logger.info("Cliente inserido com sucesso: {}", cliente.getNome());
            }

        } catch (SQLException e) {
            logger.error("Erro ao inserir cliente: {}", e.getMessage());
        }
        return isInserido;
    }

    // Método para listar todos os clientes
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getLong("id"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setSenha(resultado.getString("senha"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setDataCriacao(resultado.getDate("data_criacao").toLocalDate());

                clientes.add(cliente);
            }
            logger.info("Lista de clientes recuperada com sucesso.");

        } catch (SQLException e) {
            logger.error("Erro ao listar clientes: {}", e.getMessage());
        }
        return clientes;
    }

    // Método para atualizar um cliente no banco de dados
    public boolean atualizar(Cliente cliente) {
        boolean isAtualizado = false;

        if (cliente.getId() == null) {
            logger.warn("ID do cliente não pode ser nulo. Atualização não realizada.");
            throw new IllegalArgumentException("ID do cliente não pode ser nulo.");
        }

        String sql = "UPDATE cliente SET nome = ?, email = ?, senha = ?, cpf = ?, telefone = ? WHERE id = ?";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getSenha());
            statement.setString(4, cliente.getCpf());
            statement.setString(5, cliente.getTelefone());
            statement.setLong(6, cliente.getId());

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                isAtualizado = true;
                logger.info("Cliente atualizado com sucesso: {}", cliente.getId());
            }

        } catch (SQLException e) {
            logger.error("Erro ao atualizar cliente: {}", e.getMessage());
        }
        return isAtualizado;
    }

    // Método para excluir um cliente do banco de dados
    public void excluir(Long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("Cliente excluído com sucesso: {}", id);

        } catch (SQLException e) {
            logger.error("Erro ao excluir cliente: {}", e.getMessage());
        }
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarPorId(Long id) {
        Cliente cliente = null;
        String sql = "SELECT id, nome, email, senha, cpf, telefone, data_criacao FROM cliente WHERE id = ?";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                cliente = new Cliente();
                cliente.setId(resultado.getLong("id"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setSenha(resultado.getString("senha"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setDataCriacao(resultado.getDate("data_criacao").toLocalDate());
                logger.info("Cliente encontrado: {}", cliente.getNome());
            } else {
                logger.warn("Nenhum cliente encontrado com o ID: {}", id);
            }

        } catch (SQLException e) {
            logger.error("Erro ao buscar cliente por ID: {}", e.getMessage());
        }
        return cliente;
    }
}

