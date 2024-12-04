package br.com.professorclaytonandrade.sistemaservicosjavafx.controller;

import br.com.professorclaytonandrade.sistemaservicosjavafx.util.Util;
import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StartViewController {

    @FXML
    private AnchorPane anchorPane;

    private static AnchorPane globalAnchorPane;

    public StartViewController() {
    }

    @FXML
    public void initialize() {
        globalAnchorPane = anchorPane; // Armazenar referência do AnchorPane
    }

    @FXML
    public void mostrarCadastroTecnico() throws IOException {
        Util.janelaModal(globalAnchorPane, "cadastro-tecnico.fxml", "Cadastro Técnico");
    }
    @FXML
    public void mostrarCadastroCliente() throws IOException {
        Util.janelaModal(globalAnchorPane, "cadastro-cliente.fxml", "Cadastro Cliente");
    }
    @FXML
    public void mostrarCadastroChamado() throws IOException {
        Util.janelaModal(globalAnchorPane, "cadastro-chamado.fxml", "Cadastro Chamado");
    }

    @FXML
    public void mostrarConsultaTecnico() throws IOException {
        Util.janelaModal(globalAnchorPane, "consulta-tecnico.fxml", "Consulta Técnico");
    }

    @FXML
    public void mostrarConsultaCliente() throws IOException {
        Util.janelaModal(globalAnchorPane, "consulta-cliente.fxml", "Consulta Cliente");
    }
    @FXML
    public void mostrarConsultaChamado() throws IOException {
        Util.janelaModal(globalAnchorPane, "consulta-chamado.fxml", "Consulta Chamado");
    }

    public static <T> boolean mostrarTelaEditar(String telaEdicao, String tituloTela, T objeto, Object controller) throws IOException {
        return Util.janelaModalComDados(globalAnchorPane, telaEdicao, tituloTela, objeto, controller);
    }
}