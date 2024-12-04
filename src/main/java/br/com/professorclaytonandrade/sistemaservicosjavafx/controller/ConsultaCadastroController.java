package br.com.professorclaytonandrade.sistemaservicosjavafx.controller;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ChamadoDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.service.ChamadoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ConsultaCadastroController {

    @FXML
    private TableView<ChamadoDto> tableView;

    @FXML
    private TableColumn<ChamadoDto, Long> idColumn;

    @FXML
    private TableColumn<ChamadoDto, String> tituloColumn;

    @FXML
    private TableColumn<ChamadoDto, String> prioridadeColumn;

    @FXML
    private TableColumn<ChamadoDto, String> statusColumn;

    @FXML
    private TableColumn<ChamadoDto, LocalDate> dataAberturaColumn;

    @FXML
    private TableColumn<ChamadoDto, LocalDate> dataFechamentoColumn;

    @FXML
    private Button editarButton;

    @FXML
    private Button voltarButton;

    private ObservableList<ChamadoDto> chamadoList;

    private ChamadoService chamadoService;

    public ConsultaCadastroController() {
        this.chamadoService = new ChamadoService(); // Inicializando o serviço
    }

    @FXML
    public void initialize() {
        editarButton.setDisable(true);
        chamadoList = FXCollections.observableArrayList(chamadoService.listarTodos());

        // Configuração das colunas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        prioridadeColumn.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataAberturaColumn.setCellValueFactory(new PropertyValueFactory<>("dataAbertura"));
        dataFechamentoColumn.setCellValueFactory(new PropertyValueFactory<>("dataFechamento"));

        tableView.setItems(chamadoList);

        // Habilitar o botão de editar quando um chamado for selecionado
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editarButton.setDisable(newSelection == null); // Habilita o botão se houver seleção
        });
    }

    @FXML
    public void editar() throws IOException {
        ChamadoDto chamadoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (chamadoSelecionado != null) {
            editarChamado(chamadoSelecionado);
        }
    }


    @FXML
    public void voltar() {
        Stage stage = (Stage) voltarButton.getScene().getWindow();
        stage.close();
    }

    private void editarChamado(ChamadoDto chamadoDto) throws IOException {
        boolean editarChamado = StartViewController.mostrarTelaEditar("cadastro-chamado.fxml", "Editar Chamado", chamadoDto, new CadastroChamadoController());
        if (editarChamado) recarregarTabela();
    }

    public void recarregarTabela() {
        chamadoList.clear(); // Limpa a lista existente
        chamadoList.addAll(chamadoService.listarTodos()); // Carrega novamente a lista de chamados
        tableView.setItems(chamadoList); // Atualiza a tabela
    }
}
