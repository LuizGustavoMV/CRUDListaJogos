package com.template;
import java.sql.*;
import java.util.ArrayList;

public class JogoDAO {

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
            System.out.println("[SUCESSO] Jogo \"" + jogo.getTitulo() + "\" cadastrado com êxito!");

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao cadastrar o jogo: " + e.getMessage());
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
            System.err.println("[ERRO] Falha ao listar os jogos: " + e.getMessage());
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

            if (linhasAfetadas > 0) {
                System.out.println("[SUCESSO] Jogo com ID " + jogo.getId() + " atualizado com êxito!");
            } else {
                System.out.println("[AVISO] Nenhum jogo encontrado com o ID " + jogo.getId());
            }

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao atualizar o jogo: " + e.getMessage());
        }
    }
    public void excluirJogo(int id) {
        String sql = "DELETE FROM jogo WHERE id = ?";

        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("[SUCESSO] Jogo com ID " + id + " removido com êxito!");
            } else {
                System.out.println("[AVISO] Nenhum jogo encontrado com o ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao excluir o jogo: " + e.getMessage());
        }
    }
}
