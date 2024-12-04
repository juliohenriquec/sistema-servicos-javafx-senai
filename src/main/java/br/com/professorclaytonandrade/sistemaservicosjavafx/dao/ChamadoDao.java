package br.com.professorclaytonandrade.sistemaservicosjavafx.dao;

import br.com.professorclaytonandrade.sistemaservicosjavafx.config.conexao.FabricaDeConexao;
import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ChamadoDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Prioridade;
import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Status;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Chamado;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Cliente;
import br.com.professorclaytonandrade.sistemaservicosjavafx.model.Tecnico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDao {

    private static final Logger logger = LoggerFactory.getLogger(ClienteDao.class);



    public List<ChamadoDto> findAll() {
        String query = "SELECT * FROM chamado";
        List<ChamadoDto> chamadosDto = new ArrayList<>();  // Lista de ChamadoDto
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Chamado chamado = mapResultSetToChamado(rs);  // Mapeia o ResultSet para Chamado
                ChamadoDto chamadoDto = new ChamadoDto(
                        chamado.getId(),
                        chamado.getPrioridade(),
                        chamado.getStatus(),
                        chamado.getTitulo(),
                        chamado.getObservacoes(),
                        chamado.getTecnico() != null ? chamado.getTecnico().getId() : null,
                        chamado.getCliente() != null ? chamado.getCliente().getId() : null,
                        chamado.getDataAbertura(),
                        chamado.getDataFechamento()
                );  // Cria o ChamadoDto a partir do Chamado
                chamadosDto.add(chamadoDto);  // Adiciona à lista de ChamadoDto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chamadosDto;  // Retorna a lista de ChamadoDto
    }

    public Chamado findById(Long id) {
        String query = "SELECT * FROM chamado WHERE id = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToChamado(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void criar(Chamado chamado) {
        String query = "INSERT INTO chamado (prioridade, status, titulo, observacoes, tecnico_id, cliente_id, data_abertura) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, chamado.getPrioridade().name());
            stmt.setString(2, chamado.getStatus().name());
            stmt.setString(3, chamado.getTitulo());
            stmt.setString(4, chamado.getObservacoes());
            stmt.setLong(5, chamado.getTecnico().getId());
            stmt.setLong(6, chamado.getCliente().getId());
            stmt.setDate(7, Date.valueOf(chamado.getDataAbertura()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Chamado chamado) {
        String query = "UPDATE chamado SET prioridade = ?, status = ?, titulo = ?, observacoes = ?, tecnico_id = ?, cliente_id = ?, " +
                "data_abertura = ?, data_fechamento = ? WHERE id = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, chamado.getPrioridade().name());
            stmt.setString(2, chamado.getStatus().name());
            stmt.setString(3, chamado.getTitulo());
            stmt.setString(4, chamado.getObservacoes());
            stmt.setLong(5, chamado.getTecnico().getId());
            stmt.setLong(6, chamado.getCliente().getId());
            stmt.setDate(7, Date.valueOf(chamado.getDataAbertura()));
            stmt.setDate(8, chamado.getDataFechamento() != null ? Date.valueOf(chamado.getDataFechamento()) : null);
            stmt.setLong(9, chamado.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void encerrarChamado(Long id) {
        String query = "UPDATE chamado SET status = ?, data_fechamento = ? WHERE id = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, Status.ENCERRADO.name());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setLong(3, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Chamado mapResultSetToChamado(ResultSet rs) throws SQLException {
        Chamado chamado = new Chamado();
        chamado.setId(rs.getLong("id"));
        chamado.setPrioridade(Prioridade.valueOf(rs.getString("prioridade")));
        chamado.setStatus(Status.valueOf(rs.getString("status")));
        chamado.setTitulo(rs.getString("titulo"));
        chamado.setObservacoes(rs.getString("observacoes"));

        // Buscar o Técnico e o Cliente completos com base no ID
        Long tecnicoId = rs.getLong("tecnico_id");
        Long clienteId = rs.getLong("cliente_id");

        // Aqui você pode buscar os objetos completos, assumindo que você tenha métodos para isso
        Tecnico tecnico = buscarTecnicoPorId(tecnicoId);
        Cliente cliente = buscarClientePorId(clienteId);

        chamado.setTecnico(tecnico);  // Atribui o objeto Tecnico
        chamado.setCliente(cliente);  // Atribui o objeto Cliente


        chamado.setDataAbertura(rs.getDate("data_abertura").toLocalDate());
        if (rs.getDate("data_fechamento") != null) {
            chamado.setDataFechamento(rs.getDate("data_fechamento").toLocalDate());
        }
        // Os objetos Técnico e Cliente podem ser associados separadamente no Service
        return chamado;
    }

    private Tecnico buscarTecnicoPorId(Long tecnicoId) {
        String query = "SELECT * FROM tecnico WHERE id = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setLong(1, tecnicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tecnico tecnico = new Tecnico();
                    tecnico.setId(rs.getLong("id"));
                    tecnico.setNome(rs.getString("nome"));
                    // Preencha os outros campos do Técnico conforme necessário
                    return tecnico;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Cliente buscarClientePorId(Long clienteId) {
        String query = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setLong(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setNome(rs.getString("nome"));
                    // Preencha os outros campos do Cliente conforme necessário
                    return cliente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
