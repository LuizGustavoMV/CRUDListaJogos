package com.template.controller;

import com.template.model.JogoDAO;
import com.template.model.JogoDTO;
import com.template.util.DialogUtil;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextField txtPlataforma;
    @FXML
    private TextField txtPreco;
    @FXML
    private TableView<JogoDTO> tabelaJogos;
    @FXML
    private TableColumn<JogoDTO, Integer> colId;
    @FXML
    private TableColumn<JogoDTO, String> colNome;
    @FXML
    private TableColumn<JogoDTO, String> colGenero;
    @FXML
    private TableColumn<JogoDTO, String> colPlataforma;
    @FXML
    private TableColumn<JogoDTO, Double> colPreco;

    @FXML
    private TextField txtBusca;
    @FXML
    private Label lblMensagem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        exibirMensagem("Sistema inicializado com sucesso.", true);
        atualizarTabela();
    }

    @FXML
    private void btnLimparAction() {
        limparCampos();
        exibirMensagem("Campos limpos.", true);
    }

    @FXML
    private void btnCadastrarAction() {
        try {
            if (txtNome.getText().isEmpty() || txtPreco.getText().isEmpty()) {
                exibirMensagem("Erro: Nome e Preço são obrigatórios!", false);
                DialogUtil.showError("Por favor, preencha os campos obrigatórios (Nome e Preço).");
                return;
            }

            String titulo = txtNome.getText();
            String genero = txtGenero.getText();
            String plataforma = txtPlataforma.getText();
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));

            JogoDTO jogoDTO = new JogoDTO();
            jogoDTO.setTitulo(titulo);
            jogoDTO.setGenero(genero);
            jogoDTO.setPlataforma(plataforma);
            jogoDTO.setPreco(preco);

            JogoDAO jogoDAO = new JogoDAO();
            jogoDAO.cadastrarJogo(jogoDTO);

            DialogUtil.showInfo("Jogo '" + titulo + "' cadastrado com sucesso!");
            exibirMensagem("Jogo cadastrado com sucesso!", true);
            limparCampos();
            atualizarTabela();
        } catch (NumberFormatException e) {
            DialogUtil.showError("Erro: Formato de preço inválido.");
            exibirMensagem("Erro: Formato de preço inválido.", false);
        } catch (Exception e) {
            DialogUtil.showError("Erro ao cadastrar: " + e.getMessage());
            exibirMensagem("Erro ao cadastrar.", false);
        }
    }

    @FXML
    private void btnSalvarAction() {
        try {
            if (txtId.getText().isEmpty()) {
                DialogUtil.showError("Selecione um jogo na tabela para editar.");
                exibirMensagem("Erro: Selecione um jogo na tabela para editar!", false);
                return;
            }

            int id = Integer.parseInt(txtId.getText());
            String titulo = txtNome.getText();
            String genero = txtGenero.getText();
            String plataforma = txtPlataforma.getText();
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));

            JogoDTO jogoDTO = new JogoDTO();
            jogoDTO.setId(id);
            jogoDTO.setTitulo(titulo);
            jogoDTO.setGenero(genero);
            jogoDTO.setPlataforma(plataforma);
            jogoDTO.setPreco(preco);

            JogoDAO jogoDAO = new JogoDAO();
            jogoDAO.atualizarJogo(jogoDTO);

            DialogUtil.showInfo("Jogo ID " + id + " atualizado com sucesso!");
            exibirMensagem("Jogo atualizado com sucesso!", true);
            limparCampos();
            atualizarTabela();
        } catch (Exception e) {
            DialogUtil.showError("Erro ao salvar alterações: " + e.getMessage());
            exibirMensagem("Erro ao salvar alterações.", false);
        }
    }

    @FXML
    private void btnDeletarAction() {
        try {
            if (txtId.getText().isEmpty()) {
                DialogUtil.showError("Selecione um jogo na tabela para deletar.");
                exibirMensagem("Erro: Selecione um jogo na tabela para deletar!", false);
                return;
            }

            int id = Integer.parseInt(txtId.getText());

            JogoDAO jogoDAO = new JogoDAO();
            jogoDAO.excluirJogo(id);

            DialogUtil.showInfo("Jogo ID " + id + " removido com sucesso!");
            exibirMensagem("Jogo removido com sucesso!", true);
            limparCampos();
            atualizarTabela();
        } catch (Exception e) {
            DialogUtil.showError("Erro ao deletar: " + e.getMessage());
            exibirMensagem("Erro ao deletar.", false);
        }
    }

    @FXML
    private void filtrarJogos() {
        String termo = txtBusca.getText().toLowerCase().trim();
        JogoDAO jogoDAO = new JogoDAO();
        List<JogoDTO> listaCompleta = jogoDAO.listarJogos();

        if (termo.isEmpty()) {
            tabelaJogos.setItems(FXCollections.observableArrayList(listaCompleta));
        } else {
            List<JogoDTO> listaFiltrada = listaCompleta.stream()
                    .filter(jogo -> jogo.getTitulo().toLowerCase().contains(termo)
                            || jogo.getGenero().toLowerCase().contains(termo))
                    .collect(Collectors.toList());

            tabelaJogos.setItems(FXCollections.observableArrayList(listaFiltrada));
        }
    }

    private void atualizarTabela() {
        try {
            JogoDAO jogoDAO = new JogoDAO();
            List<JogoDTO> lista = jogoDAO.listarJogos();
            tabelaJogos.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            exibirMensagem("Erro ao carregar banco de dados.", false);
        }
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtGenero.clear();
        txtPlataforma.clear();
        txtPreco.clear();
    }

    @FXML
    private void carregarCampos() {
        JogoDTO objJogoDTO = tabelaJogos.getSelectionModel().getSelectedItem();

        if (objJogoDTO != null) {
            txtNome.setText(objJogoDTO.getTitulo());
            txtGenero.setText(objJogoDTO.getGenero());
            txtPlataforma.setText(objJogoDTO.getPlataforma());
            txtPreco.setText(String.valueOf(objJogoDTO.getPreco()));
            txtId.setText(String.valueOf(objJogoDTO.getId()));
            exibirMensagem("Jogo selecionado para edição.", true);
        }
    }

    private void exibirMensagem(String mensagem, boolean ehSucesso) {
        if (lblMensagem != null) {
            lblMensagem.setText(mensagem);
            if (ehSucesso) {
                lblMensagem.setStyle("-fx-text-fill: #a3e635;");
            } else {
                lblMensagem.setStyle("-fx-text-fill: #f87171;");
            }
        }
    }
}