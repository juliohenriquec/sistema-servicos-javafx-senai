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
    public void mostrarConsultaTecnico() throws IOException {
        Util.janelaModal(globalAnchorPane, "consulta-tecnico.fxml", "Consulta Técnico");
    }

    public static <T> boolean mostrarTelaEditar(String telaEdicao, String tituloTela, T objeto, Object controller) throws IOException {
        return Util.janelaModalComDados(globalAnchorPane, telaEdicao, tituloTela, objeto, controller);
    }
}