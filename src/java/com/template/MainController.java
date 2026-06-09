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
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        System.out.println("FXML carregado com sucesso!");
        atualizarTabela();
    }
    @FXML
    private void btnCadrastarAction() {
        btnCadastrarAction();
    }
    @FXML
    private void btnLimparAction() {
        limparCampos();
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
    }

    @FXML
    private void btnDeletarAction() {
        int id = Integer.parseInt(txtId.getText());

        JogoDAO jogoDAO = new JogoDAO();
        jogoDAO.excluirJogo(id);

        atualizarTabela();
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
            int Id = objJogoDTO.getId();
            txtId.setText(String.valueOf(Id));
        }
    }
}