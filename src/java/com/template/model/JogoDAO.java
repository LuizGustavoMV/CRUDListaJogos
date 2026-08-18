package com.template.model;

import com.template.util.DialogUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JogoDAO {

    // Logger configurado para registrar exceções no log do sistema
    private static final Logger logger = Logger.getLogger(JogoDAO.class.getName());
    private final Conexao conexao;

    public JogoDAO() {
        this.conexao = new Conexao();
    }

    public void cadastrarJogo(JogoDTO jogo) {
        String sql = "INSERT INTO jogo (titulo, genero, plataforma, preco) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, jogo.getTitulo());
            ps.setString(2, jogo.getGenero());
            ps.setString(3, jogo.getPlataforma());
            ps.setDouble(4, jogo.getPreco());

            ps.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar jogo", e);
            DialogUtil.showError("Erro ao cadastrar jogo no banco de dados.");
        }
    }

    public ArrayList<JogoDTO> listarJogos() {
        String sql = "SELECT * FROM jogo ORDER BY id ASC";
        ArrayList<JogoDTO> listaJogos = new ArrayList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                JogoDTO jogo = new JogoDTO();
                jogo.setId(rs.getInt("id"));
                jogo.setTitulo(rs.getString("titulo"));
                jogo.setGenero(rs.getString("genero"));
                jogo.setPlataforma(rs.getString("plataforma"));
                jogo.setPreco(rs.getDouble("preco"));
                listaJogos.add(jogo);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar jogos", e);
            DialogUtil.showError("Erro ao listar jogos do banco de dados.");
        }

        return listaJogos;
    }

    public void atualizarJogo(JogoDTO jogo) {
        String sql = "UPDATE jogo SET titulo = ?, genero = ?, plataforma = ?, preco = ? WHERE id = ?";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, jogo.getTitulo());
            ps.setString(2, jogo.getGenero());
            ps.setString(3, jogo.getPlataforma());
            ps.setDouble(4, jogo.getPreco());
            ps.setInt(5, jogo.getId());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) {
                DialogUtil.showError("Nenhum jogo encontrado com o ID " + jogo.getId());
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar jogo", e);
            DialogUtil.showError("Erro ao atualizar jogo no banco de dados.");
        }
    }

    public void excluirJogo(int id) {
        String sql = "DELETE FROM jogo WHERE id = ?";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) {
                DialogUtil.showError("Nenhum jogo encontrado com o ID " + id);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir jogo", e);
            DialogUtil.showError("Erro ao excluir jogo do banco de dados.");
        }
    }
}