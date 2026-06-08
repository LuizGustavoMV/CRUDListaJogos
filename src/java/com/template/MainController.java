package com.template;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class MainController {

    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCadastrar;

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

    // CORREÇÃO 1: Alterado de colID para colId (combinando com o padrão de colPreco, colNome, etc. do FXML)
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
    private void initialize() {
        // Uso do colId corrigido aqui também
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        System.out.println("FXML carregado com sucesso!");
        atualizarTabela();
    }

    // CORREÇÃO 2: Criado este método alternativo caso o FXML chame "btnCadrastarAction" com erro de digitação
    @FXML
    private void btnCadrastarAction() {
        btnCadastrarAction();
    }

    @FXML
    private void btnCadastrarAction() {
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

        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void btnEditarAction() {
        JogoDTO jogoSelecionado = tabelaJogos.getSelectionModel().getSelectedItem();

        if (jogoSelecionado != null) {
            txtId.setText(String.valueOf(jogoSelecionado.getId()));
            txtNome.setText(jogoSelecionado.getTitulo());
            txtGenero.setText(jogoSelecionado.getGenero());
            txtPlataforma.setText(jogoSelecionado.getPlataforma());
            txtPreco.setText(String.valueOf(jogoSelecionado.getPreco()));
        }
    }

    @FXML
    private void btnSalvarAction() {
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

        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void btnDeletarAction() {
        int id = Integer.parseInt(txtId.getText());

        JogoDAO jogoDAO = new JogoDAO();
        jogoDAO.excluirJogo(id);

        atualizarTabela();
        limparCampos();
    }

    private void atualizarTabela() {
        JogoDAO jogoDAO = new JogoDAO();
        List<JogoDTO> lista = jogoDAO.listarJogos();
        tabelaJogos.getItems().setAll(lista);
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
            double Preco = objJogoDTO.getPreco();
            txtPreco.setText(String.valueOf(Preco));
        }
    }
}