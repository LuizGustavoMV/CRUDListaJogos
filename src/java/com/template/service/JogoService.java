package com.template.service;

import com.template.model.JogoDAO;
import com.template.model.JogoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class JogoService {

    private final JogoDAO jogoDAO;

    public JogoService() {
        this.jogoDAO = new JogoDAO();
    }

    public void cadastrar(JogoDTO jogo) {
        jogoDAO.cadastrarJogo(jogo);
    }

    public void atualizar(JogoDTO jogo) {
        jogoDAO.atualizarJogo(jogo);
    }

    public void excluir(int id) {
        jogoDAO.excluirJogo(id);
    }

    public List<JogoDTO> listar() {
        return jogoDAO.listarJogos();
    }

    public List<JogoDTO> buscarPorTermo(String termo) {
        List<JogoDTO> todos = listar();
        if (termo == null || termo.trim().isEmpty()) {
            return todos;
        }
        String termoLower = termo.toLowerCase().trim();
        return todos.stream()
                .filter(jogo -> (jogo.getTitulo() != null && jogo.getTitulo().toLowerCase().contains(termoLower))
                        || (jogo.getGenero() != null && jogo.getGenero().toLowerCase().contains(termoLower)))
                .collect(Collectors.toList());
    }
}