package com.template.controller;

import com.template.exception.ValidationException;
import com.template.model.JogoDTO;
import com.template.service.JogoService;
import com.template.util.DialogUtil;
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

    private final JogoService jogoService = new JogoService();

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
            JogoDTO dto = extrairDTODosCampos(false);
            jogoService.cadastrar(dto);

            notificarSucesso("Jogo '" + dto.getTitulo() + "' cadastrado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (ValidationException e) {
            notificarErro(e.getMessage());
        } catch (Exception e) {
            notificarErro("Erro inesperado ao cadastrar o jogo.");
        }
    }

    @FXML
    private void btnSalvarAction() {
        try {
            JogoDTO dto = extrairDTODosCampos(true);
            jogoService.atualizar(dto);

            notificarSucesso("Jogo ID " + dto.getId() + " atualizado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (ValidationException e) {
            notificarErro(e.getMessage());
        } catch (Exception e) {
            notificarErro("Erro inesperado ao atualizar o jogo.");
        }
    }

    @FXML
    private void btnDeletarAction() {
        try {
            int id = JogoValidator.validarEConverterId(txtId.getText());
            jogoService.excluir(id);

            notificarSucesso("Jogo ID " + id + " removido com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (ValidationException e) {
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

    private JogoDTO extrairDTODosCampos(boolean precisaId) {
        int id = precisaId ? JogoValidator.validarEConverterId(txtId.getText()) : 0;
        JogoValidator.validarCamposObrigatorios(txtNome.getText());
        double preco = JogoValidator.validarEConverterPreco(txtPreco.getText());

        return new JogoDTO(id, txtNome.getText(), txtGenero.getText(), txtPlataforma.getText(), preco);
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