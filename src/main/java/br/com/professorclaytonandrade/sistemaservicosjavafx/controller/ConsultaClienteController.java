package br.com.professorclaytonandrade.sistemaservicosjavafx.controller;

import br.com.professorclaytonandrade.sistemaservicosjavafx.dto.ClienteDto;
import br.com.professorclaytonandrade.sistemaservicosjavafx.service.ClienteService;
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

public class ConsultaClienteController {

    @FXML
    private TableView<ClienteDto> tableView;

    @FXML
    private TableColumn<ClienteDto, Long> idColumn;

    @FXML
    private TableColumn<ClienteDto, String> nomeColumn;

    @FXML
    private TableColumn<ClienteDto, String> emailColumn;

    @FXML
    private TableColumn<ClienteDto, String> cpfColumn;

    @FXML
    private TableColumn<ClienteDto, String> telefoneColumn;

    @FXML
    private TableColumn<ClienteDto, LocalDate> dataCriacaoColumn;

    @FXML
    private Button editarButton;

    @FXML
    private Button voltarButton;

    private ObservableList<ClienteDto> clienteList;

    private ClienteService clienteService;

    public ConsultaClienteController() {
        this.clienteService = new ClienteService(); // Inicializando o serviço;
    }

    @FXML
    public void initialize() {
        editarButton.setDisable(true);
        clienteList = FXCollections.observableArrayList(clienteService.listarTodos());

        // Configuração das colunas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        dataCriacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));

        tableView.setItems(clienteList);

        // Habilitar o botão de editar quando um cliente for selecionado
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editarButton.setDisable(newSelection == null); // Habilita o botão se houver seleção
        });
    }

    @FXML
    public void editar() throws IOException {
        ClienteDto clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            editarCliente(clienteSelecionado);
        }
    }

    @FXML
    public void deletar() {
        ClienteDto clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            clienteService.remover(clienteSelecionado.getId());
            recarregarTabela();
        }
    }

    @FXML
    public void voltar() {
        Stage stage = (Stage) voltarButton.getScene().getWindow();
        stage.close();
    }

    private void editarCliente(ClienteDto clienteDto) throws IOException {
        boolean editarCliente = StartViewController.mostrarTelaEditar("cadastro-cliente.fxml", "Editar Cliente", clienteDto, new CadastroClienteController());
        if (editarCliente) recarregarTabela();
    }

    public void recarregarTabela() {
        clienteList.clear(); // Limpa a lista existente
        clienteList.addAll(clienteService.listarTodos()); // Carrega novamente a lista de clientes
        tableView.setItems(clienteList); // Atualiza a tabela
    }
}
