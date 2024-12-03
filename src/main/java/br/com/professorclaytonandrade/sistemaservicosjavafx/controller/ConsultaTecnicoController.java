package br.com.professorclaytonandrade.sistemaservicosjavafx.controller;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.TecnicoDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.service.TecnicoService;
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

public class ConsultaTecnicoController {

    @FXML
    private TableView<TecnicoDto> tableView;

    @FXML
    private TableColumn<TecnicoDto, Long> idColumn;

    @FXML
    private TableColumn<TecnicoDto, String> nomeColumn;

    @FXML
    private TableColumn<TecnicoDto, String> emailColumn;

    @FXML
    private TableColumn<TecnicoDto, String> cpfColumn;

    @FXML
    private TableColumn<TecnicoDto, Double> salarioColumn;

    @FXML
    private TableColumn<TecnicoDto, LocalDate> dataCriacaoColumn;

    @FXML
    private Button editarButton;

    @FXML
    private Button voltarButton;

    private ObservableList<TecnicoDto> tecnicoList;

    private TecnicoService tecnicoService;

    public ConsultaTecnicoController() {
        this.tecnicoService = new TecnicoService(); // Inicializando o serviço;
    }

    @FXML
    public void initialize() {
        editarButton.setDisable(true);
        tecnicoList = FXCollections.observableArrayList(tecnicoService.listarTodos());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));
        dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));

        tableView.setItems(tecnicoList);

        // Habilitar o botão de editar quando um técnico for selecionado
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editarButton.setDisable(newSelection == null); // Habilita o botão se houver seleção
        });
    }

    @FXML
    public void editar() throws IOException {
        TecnicoDto tecnicoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (tecnicoSelecionado != null) {
            editarTecnico(tecnicoSelecionado);
        }
    }

    @FXML
    public void deletar() {
        TecnicoDto tecnicoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (tecnicoSelecionado != null) {
            tecnicoService.remover(tecnicoSelecionado.getId());
            recarregarTabela();
        }
    }

    @FXML
    public void voltar() {
        Stage stage = (Stage) voltarButton.getScene().getWindow();
        stage.close();
    }

    private void editarTecnico(TecnicoDto tecnicoDto) throws IOException {
        boolean editarTécnico = StartViewController.mostrarTelaEditar("cadastro-tecnico.fxml", "Editar Técnico", tecnicoDto, new CadastroTecnicoController());
        if(editarTécnico)recarregarTabela();
    }

    public void recarregarTabela() {
        tecnicoList.clear(); // Limpa a lista existente
        tecnicoList.addAll(tecnicoService.listarTodos()); // Carrega novamente a lista de técnicos
        tableView.setItems(tecnicoList); // Atualiza a tabela
    }

}

