package br.com.professorclaytonandrade.sistemaservicosjavafx.controller;


import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.TecnicoDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.service.TecnicoService;
import br.com.professorclaytonandrade.sistemaservicosjavafx.util.Mensagens;
import br.com.professorclaytonandrade.sistemaservicosjavafx.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CadastroTecnicoController {
    private static final Logger logger = LoggerFactory.getLogger(TecnicoService.class);

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
    private TextField salarioField;

    @FXML
    private PasswordField senhaField;

    private TecnicoService tecnicoService;

    private boolean edicao =  false;

    public CadastroTecnicoController() {
        this.tecnicoService = new TecnicoService(); // Inicializando o serviço;
    }

    @FXML
    public void initialize() {

        ChangeListener<String> campoListener = (observable, oldValue, newValue) ->
                salvarButton.setDisable(!todosCamposPreenchidos());
        nomeField.textProperty().addListener(campoListener);
        emailField.textProperty().addListener(campoListener);
        senhaField.textProperty().addListener(campoListener);
        cpfField.textProperty().addListener(campoListener);
        salarioField.textProperty().addListener(campoListener);
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
            if(edicao) fecharJanela();
        } catch (IllegalArgumentException e) {
            Util.exibirAlerta(Alert.AlertType.ERROR, Mensagens.TITULO_ERRO_VALIDACAO, null, Mensagens.MSG_ERRO_AO_SALVAR_INFORMACOES + e.getMessage());
        } catch (Exception e) {
            Util.exibirAlerta(Alert.AlertType.ERROR, Mensagens.TITULO_ERRO_AO_SALVAR, null, Mensagens.MSG_ERRO_AO_SALVAR_INFORMACOES + e.getMessage());
        }
    }

    private void salvarAlteracoes() {
        Long id = !idField.getText().trim().isEmpty() ? Long.valueOf(idField.getText().trim()) : null;
        Double salario = !salarioField.getText().isEmpty() ? Double.valueOf(salarioField.getText().trim()) : null;
        TecnicoDto tecnicoDto = new TecnicoDto(id, nomeField.getText().trim(), emailField.getText().trim(), senhaField.getText().trim(), cpfField.getText().trim(), salario, dataCriacaoField.getValue());
        if (tecnicoDto.getId() == null ) {
            tecnicoService.criar(tecnicoDto); // Adiciona novo técnico
        } else {
            tecnicoService.atualizar(tecnicoDto); // Atualiza técnico existente
            edicao = true;
        }
    }

    public void setDado(TecnicoDto tecnicoDto) {
        if (tecnicoDto != null) {
            idField.setText(String.valueOf(tecnicoDto.getId()));
            nomeField.setText(tecnicoDto.getNome());
            emailField.setText(tecnicoDto.getEmail());
            senhaField.setText(tecnicoDto.getSenha());
            cpfField.setText(tecnicoDto.getCpf());
            salarioField.setText(String.valueOf(tecnicoDto.getSalario()));
            dataCriacaoField.setValue(tecnicoDto.getDataCriacao());
            if (tecnicoDto.getDataCriacao() != null) {
                dataCriacaoField.setValue(tecnicoDto.getDataCriacao());
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
        salarioField.clear();
        dataCriacaoField.setValue(null);
    }

    private boolean todosCamposPreenchidos() {
        return !nomeField.getText().trim().isEmpty() &&
                !emailField.getText().trim().isEmpty() &&
                !senhaField.getText().trim().isEmpty() &&
                !cpfField.getText().trim().isEmpty() &&
                !salarioField.getText().trim().isEmpty() &&
                dataCriacaoField.getValue() != null;
    }

    private void fecharJanela() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
