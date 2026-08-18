package com.template.service;

import com.template.model.JogoDAO;
import com.template.model.dto.JogoDTO;

import java.util.ArrayList;
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
        List<JogoDTO> filtrados = new ArrayList<>();

        for (JogoDTO jogo : todos) {
            String titulo = jogo.getTitulo() != null ? jogo.getTitulo().toLowerCase() : "";
            String genero = jogo.getGenero() != null ? jogo.getGenero().toLowerCase() : "";

            if (titulo.contains(termoLower) || genero.contains(termoLower)) {
                filtrados.add(jogo);
            }
        }

        return filtrados;
    }
}