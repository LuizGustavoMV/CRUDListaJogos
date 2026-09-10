package com.template.service;

import com.template.model.dao.JogoDAO;
import com.template.model.dto.JogoDTO;

import java.util.ArrayList;
import java.util.List;

public class JogoService implements IJogoService {

    private final JogoDAO jogoDAO;

    public JogoService() {
        this.jogoDAO = new JogoDAO();
    }

    @Override
    public void cadastrar(JogoDTO jogo) {
        jogoDAO.cadastrarJogo(jogo);
    }

    @Override
    public void atualizar(JogoDTO jogo) {
        jogoDAO.atualizarJogo(jogo);
    }

    @Override
    public void excluir(int id) {
        jogoDAO.excluirJogo(id);
    }

    @Override
    public List<JogoDTO> listar() {
        return jogoDAO.listarJogos();
    }

    @Override
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