package br.com.professorclaytonandrade.sistemaservicosjavafx.dao;

import br.com.professorclaytonandrade.sistemaservicosjavafx.config.conexao.FabricaDeConexao;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Tecnico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDao {

    private static final Logger logger = LoggerFactory.getLogger(TecnicoDao.class);

    // Método para inserir um técnico no banco de dados
    public boolean inserir(Tecnico tecnico) {
        boolean isInserido = false; // Variável para armazenar o status da inserção

        tecnico.setId(null);
        String sql = "INSERT INTO tecnico (nome, email, senha, cpf, salario, data_criacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, tecnico.getNome());
            statement.setString(2, tecnico.getEmail());
            statement.setString(3, tecnico.getSenha());
            statement.setString(4, tecnico.getCpf());
            statement.setDouble(5, tecnico.getSalario());
            statement.setDate(6, Date.valueOf(tecnico.getDataCriacao()));

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                isInserido = true; // A inserção foi bem-sucedida
                logger.info("Técnico inserido com sucesso: {}", tecnico.getNome());
            }

        } catch (SQLException e) {
            logger.error("Erro ao inserir técnico: {}", e.getMessage());
        }
        return isInserido; // Retorna o status da inserção
    }

    // Método para listar todos os técnicos
    public List<Tecnico> listar() {
        List<Tecnico> tecnicos = new ArrayList<>();
        String sql = "SELECT * FROM tecnico";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                Tecnico tecnico = new Tecnico();
                tecnico.setId(resultado.getLong("id"));
                tecnico.setNome(resultado.getString("nome"));
                tecnico.setEmail(resultado.getString("email"));
                tecnico.setSenha(resultado.getString("senha"));
                tecnico.setCpf(resultado.getString("cpf"));
                tecnico.setSalario(resultado.getDouble("salario"));
                tecnico.setDataCriacao(resultado.getDate("data_criacao") != null ? resultado.getDate("data_criacao").toLocalDate() : null);

                tecnicos.add(tecnico);
            }
            logger.info("Lista de técnicos recuperada com sucesso.");

        } catch (SQLException e) {
            logger.error("Erro ao listar técnicos: {}", e.getMessage());
        }
        return tecnicos;
    }

    // Método para atualizar um técnico no banco de dados
    public boolean atualizar(Tecnico tecnico) {
        boolean isInserido = false; // Variável para armazenar o status da inserção

        if (tecnico.getId() == null) {
            logger.warn("ID do técnico não pode ser nulo. Atualização não realizada.");
            throw new IllegalArgumentException("ID do técnico não pode ser nulo.");
        }

        String sql = "UPDATE tecnico SET nome = ?, email = ?, senha = ?, cpf = ?, salario = ? WHERE id = ?";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, tecnico.getNome());
            statement.setString(2, tecnico.getEmail());
            statement.setString(3, tecnico.getSenha());
            statement.setString(4, tecnico.getCpf());
            statement.setDouble(5, tecnico.getSalario());
            statement.setLong(6, tecnico.getId());

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                isInserido = true; // A atualização foi bem-sucedida
                logger.info("Técnico atualizado com sucesso: {}", tecnico.getId());
            }

        } catch (SQLException e) {
            logger.error("Erro ao atualizar técnico: {}", e.getMessage());
        }
        return isInserido;
    }

    // Método para excluir um técnico do banco de dados
    public void excluir(Long id) {
        String sql = "DELETE FROM tecnico WHERE id = ?";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("Técnico excluído com sucesso: {}", id);

        } catch (SQLException e) {
            logger.error("Erro ao excluir técnico: {}", e.getMessage());
        }
    }

    // Método para buscar um técnico pelo ID
    public Tecnico buscarPorId(Long id) {
        Tecnico tecnico = null;
        String sql = "SELECT id, nome, email, senha, cpf, salario, data_criacao FROM tecnico WHERE id = ?";

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                tecnico = new Tecnico();
                tecnico.setId(resultado.getLong("id"));
                tecnico.setNome(resultado.getString("nome"));
                tecnico.setEmail(resultado.getString("email"));
                tecnico.setSenha(resultado.getString("senha"));
                tecnico.setCpf(resultado.getString("cpf"));
                tecnico.setSalario(resultado.getDouble("salario"));
                tecnico.setDataCriacao(resultado.getDate("data_criacao").toLocalDate());
                logger.info("Técnico encontrado: {}", tecnico.getNome());
            } else {
                logger.warn("Nenhum técnico encontrado com o ID: {}", id);
            }

        } catch (SQLException e) {
            logger.error("Erro ao buscar técnico por ID: {}", e.getMessage());
        }
        return tecnico;
    }
}
