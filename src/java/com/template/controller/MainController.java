package com.template.controller;

import com.template.model.dto.JogoDTO;
import com.template.service.IJogoService;
import com.template.service.JogoService;
import com.template.util.DialogUtil;
import com.template.validator.IJogoValidator;
import com.template.validator.JogoValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Button btnSalvar;
    @FXML private Button btnDeletar;
    @FXML private Button btnCadastrar;
    @FXML private Button btnLimpar;

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtGenero;
    @FXML private TextField txtPlataforma;
    @FXML private TextField txtPreco;
    @FXML private TextField txtBusca;

    @FXML private TableView<JogoDTO> tabelaJogos;
    @FXML private TableColumn<JogoDTO, Integer> colId;
    @FXML private TableColumn<JogoDTO, String> colNome;
    @FXML private TableColumn<JogoDTO, String> colGenero;
    @FXML private TableColumn<JogoDTO, String> colPlataforma;
    @FXML private TableColumn<JogoDTO, Double> colPreco;

    @FXML private Label lblMensagem;

    // Depende das Abstrações (Interfaces)
    private final IJogoService jogoService;
    private final IJogoValidator jogoValidator;

    // Construtor padrão necessário para o JavaFX (FXMLLoader)
    public MainController() {
        this(new JogoService(), new JogoValidator());
    }

    // Injeção de dependência via Construtor
    public MainController(IJogoService jogoService, IJogoValidator jogoValidator) {
        this.jogoService = jogoService;
        this.jogoValidator = jogoValidator;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunasTabela();
        atualizarTabela();
        exibirMensagem("Sistema inicializado com sucesso.", true);
    }

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    @FXML
    private void btnCadastrarAction() {
        try {
            jogoValidator.validarCampos(txtNome.getText(), txtPlataforma.getText(), txtPreco.getText());

            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            JogoDTO dto = new JogoDTO(0, txtNome.getText(), txtGenero.getText(), txtPlataforma.getText(), preco);

            jogoService.cadastrar(dto);

            notificarSucesso("Jogo '" + dto.getTitulo() + "' cadastrado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (IllegalArgumentException e) {
            notificarErro(e.getMessage());
        } catch (Exception e) {
            notificarErro("Erro inesperado ao cadastrar o jogo.");
        }
    }

    @FXML
    private void btnSalvarAction() {
        try {
            int id = jogoValidator.validarEConverterId(txtId.getText());
            jogoValidator.validarCampos(txtNome.getText(), txtPlataforma.getText(), txtPreco.getText());

            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            JogoDTO dto = new JogoDTO(id, txtNome.getText(), txtGenero.getText(), txtPlataforma.getText(), preco);

            jogoService.atualizar(dto);

            notificarSucesso("Jogo ID " + id + " atualizado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (IllegalArgumentException e) {
            notificarErro(e.getMessage());
        } catch (Exception e) {
            notificarErro("Erro inesperado ao atualizar o jogo.");
        }
    }

    @FXML
    private void btnDeletarAction() {
        try {
            int id = jogoValidator.validarEConverterId(txtId.getText());

            jogoService.excluir(id);

            notificarSucesso("Jogo ID " + id + " removido com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (IllegalArgumentException e) {
            notificarErro(e.getMessage());
        } catch (Exception e) {
            notificarErro("Erro inesperado ao deletar o jogo.");
        }
    }

    @FXML
    private void filtrarJogos() {
        var resultado = jogoService.buscarPorTermo(txtBusca.getText());
        tabelaJogos.setItems(FXCollections.observableArrayList(resultado));
    }

    @FXML
    private void btnLimparAction() {
        limparCampos();
        exibirMensagem("Campos limpos.", true);
    }

    @FXML
    private void carregarCampos() {
        JogoDTO selecionado = tabelaJogos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            txtId.setText(String.valueOf(selecionado.getId()));
            txtNome.setText(selecionado.getTitulo());
            txtGenero.setText(selecionado.getGenero());
            txtPlataforma.setText(selecionado.getPlataforma());
            txtPreco.setText(String.valueOf(selecionado.getPreco()));
            exibirMensagem("Jogo selecionado para edição.", true);
        }
    }

    private void atualizarTabela() {
        tabelaJogos.setItems(FXCollections.observableArrayList(jogoService.listar()));
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtGenero.clear();
        txtPlataforma.clear();
        txtPreco.clear();
    }

    private void notificarSucesso(String msg) {
        exibirMensagem(msg, true);
        DialogUtil.showInfo(msg);
    }

    private void notificarErro(String msg) {
        exibirMensagem(msg, false);
        DialogUtil.showError(msg);
    }

    private void exibirMensagem(String mensagem, boolean ehSucesso) {
        if (lblMensagem != null) {
            lblMensagem.setText(mensagem);
            lblMensagem.setStyle(ehSucesso ? "-fx-text-fill: #a3e635;" : "-fx-text-fill: #f87171;");
        }
    }
}