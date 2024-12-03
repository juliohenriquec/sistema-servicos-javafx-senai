package br.com.professorclaytonandrade.sistemaservicosjavafx.controller;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ClienteDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.service.ClienteService;
import br.com.professorclaytonandrade.sistemaservicosjavafx.util.Mensagens;
import br.com.professorclaytonandrade.sistemaservicosjavafx.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CadastroClienteController {
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @FXML
    private Button cancelarButton;

    @FXML
    private Button salvarButton;

    @FXML
    private TextField cpfField;

    @FXML
    private DatePicker dataCriacaoField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField idField;

    @FXML
    private TextField nomeField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private TextField telefoneField; // Campo para o telefone

    private ClienteService clienteService;

    private boolean edicao = false;

    public CadastroClienteController() {
        this.clienteService = new ClienteService(); // Inicializando o serviço
    }

    @FXML
    public void initialize() {

        ChangeListener<String> campoListener = (observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos());
        nomeField.textProperty().addListener(campoListener);
        emailField.textProperty().addListener(campoListener);
        senhaField.textProperty().addListener(campoListener);
        cpfField.textProperty().addListener(campoListener);
        telefoneField.textProperty().addListener(campoListener);
        dataCriacaoField.valueProperty().addListener((observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos()));

        // Inicialmente, o botão "Salvar" está desativado.
        salvarButton.setDisable(true);
    }

    @FXML
    void cancelar() {
        fecharJanela();
    }

    @FXML
    void salvar() {
        try {
            salvarAlteracoes();
            Util.exibirAlerta(Alert.AlertType.INFORMATION, Mensagens.TITULO_CONFIRMACAO_SALVAMENTO, null, Mensagens.MSG_INF_SALVAS_COM_SUCESSO);
            limparCampos();
            if (edicao) fecharJanela();
        } catch (IllegalArgumentException e) {
            Util.exibirAlerta(Alert.AlertType.ERROR, Mensagens.TITULO_ERRO_VALIDACAO, null, Mensagens.MSG_ERRO_AO_SALVAR_INFORMACOES + e.getMessage());
        } catch (Exception e) {
            Util.exibirAlerta(Alert.AlertType.ERROR, Mensagens.TITULO_ERRO_AO_SALVAR, null, Mensagens.MSG_ERRO_AO_SALVAR_INFORMACOES + e.getMessage());
        }
    }

    private void salvarAlteracoes() {
        Long id = !idField.getText().trim().isEmpty() ? Long.valueOf(idField.getText().trim()) : null;
        ClienteDto clienteDto = new ClienteDto(id, nomeField.getText().trim(), emailField.getText().trim(), senhaField.getText().trim(), cpfField.getText().trim(),telefoneField.getText().trim(), dataCriacaoField.getValue());
        if (clienteDto.getId() == null) {
            clienteService.criar(clienteDto); // Adiciona novo cliente
        } else {
            clienteService.atualizar(clienteDto); // Atualiza cliente existente
            edicao = true;
        }
    }

    public void setDado(ClienteDto clienteDto) {
        if (clienteDto != null) {
            idField.setText(String.valueOf(clienteDto.getId()));
            nomeField.setText(clienteDto.getNome());
            emailField.setText(clienteDto.getEmail());
            senhaField.setText(clienteDto.getSenha());
            cpfField.setText(clienteDto.getCpf());
            telefoneField.setText(clienteDto.getTelefone());
            if (clienteDto.getDataCriacao() != null) {
                dataCriacaoField.setValue(clienteDto.getDataCriacao());
            }
        }
    }

    private void limparCampos() {
        // Limpar todos os campos
        idField.clear();
        nomeField.clear();
        emailField.clear();
        senhaField.clear();
        cpfField.clear();
        telefoneField.clear();
        dataCriacaoField.setValue(null);
    }

    private boolean todosCamposPreenchidos() {
        return !nomeField.getText().trim().isEmpty() &&
                !emailField.getText().trim().isEmpty() &&
                !senhaField.getText().trim().isEmpty() &&
                !cpfField.getText().trim().isEmpty() &&
                !telefoneField.getText().trim().isEmpty() &&
                dataCriacaoField.getValue() != null;
    }

    private void fecharJanela() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
