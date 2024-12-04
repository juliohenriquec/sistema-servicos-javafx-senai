package br.com.professorclaytonandrade.sistemaservicosjavafx.controller;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ChamadoDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Prioridade;
import br.com.professorclaytonandrade.sistemaservicosjavafx.enuns.Status;
import br.com.professorclaytonandrade.sistemaservicosjavafx.service.ChamadoService;
import br.com.professorclaytonandrade.sistemaservicosjavafx.util.Mensagens;
import br.com.professorclaytonandrade.sistemaservicosjavafx.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CadastroChamadoController {

    private static final Logger logger = LoggerFactory.getLogger(CadastroChamadoController.class);

    @FXML
    private Button cancelarButton;

    @FXML
    private Button salvarButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField tituloField;

    @FXML
    private TextArea observacoesArea;

    @FXML
    private ComboBox<Prioridade> prioridadeComboBox;

    @FXML
    private ComboBox<Status> statusComboBox;

    @FXML
    private TextField tecnicoIdField;

    @FXML
    private TextField clienteIdField;

    @FXML
    private DatePicker dataAberturaField;

    @FXML
    private DatePicker dataFechamentoField;

    private ChamadoService chamadoService;

    private boolean edicao = false;

    public CadastroChamadoController() {
        this.chamadoService = new ChamadoService(); // Inicializando o serviço
    }

    @FXML
    public void initialize() {

        // Popula os ComboBoxes com os valores dos enums
        prioridadeComboBox.getItems().setAll(Prioridade.values());
        statusComboBox.getItems().setAll(Status.values());

        ChangeListener<String> campoListener = (observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos());
        tituloField.textProperty().addListener(campoListener);
        observacoesArea.textProperty().addListener((observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos()));
        tecnicoIdField.textProperty().addListener(campoListener);
        clienteIdField.textProperty().addListener(campoListener);
        prioridadeComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos()));
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos()));
        dataAberturaField.valueProperty().addListener((observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos()));

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
        ChamadoDto chamadoDto = new ChamadoDto(
                id,
                prioridadeComboBox.getValue(),
                statusComboBox.getValue(),
                tituloField.getText().trim(),
                observacoesArea.getText().trim(),
                Long.valueOf(tecnicoIdField.getText().trim()),
                Long.valueOf(clienteIdField.getText().trim()),
                dataAberturaField.getValue(),
                dataFechamentoField.getValue()
        );

        if (chamadoDto.getId() == null) {
            chamadoService.criar(chamadoDto); // Adiciona novo chamado
        } else {
            chamadoService.atualizar(chamadoDto); // Atualiza chamado existente
            edicao = true;
        }
    }

    public void setDado(ChamadoDto chamadoDto) {
        if (chamadoDto != null) {
            idField.setText(String.valueOf(chamadoDto.getId()));
            tituloField.setText(chamadoDto.getTitulo());
            observacoesArea.setText(chamadoDto.getObservacoes());
            prioridadeComboBox.setValue(chamadoDto.getPrioridade());
            statusComboBox.setValue(chamadoDto.getStatus());
            tecnicoIdField.setText(String.valueOf(chamadoDto.getTecnicoId()));
            clienteIdField.setText(String.valueOf(chamadoDto.getClienteId()));
            dataAberturaField.setValue(chamadoDto.getDataAbertura());
            dataFechamentoField.setValue(chamadoDto.getDataFechamento());
        }
    }

    private void limparCampos() {
        idField.clear();
        tituloField.clear();
        observacoesArea.clear();
        prioridadeComboBox.setValue(null);
        statusComboBox.setValue(null);
        tecnicoIdField.clear();
        clienteIdField.clear();
        dataAberturaField.setValue(null);
        dataFechamentoField.setValue(null);
    }

    private boolean todosCamposPreenchidos() {
        return !tituloField.getText().trim().isEmpty() &&
                !observacoesArea.getText().trim().isEmpty() &&
                prioridadeComboBox.getValue() != null &&
                statusComboBox.getValue() != null &&
                !tecnicoIdField.getText().trim().isEmpty() &&
                !clienteIdField.getText().trim().isEmpty() &&
                dataAberturaField.getValue() != null;
    }

    private void fecharJanela() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
