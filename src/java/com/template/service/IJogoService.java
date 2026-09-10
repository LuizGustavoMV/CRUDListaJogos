package com.template.service;

import com.template.model.dto.JogoDTO;
import java.util.List;

public interface IJogoService {
    void cadastrar(JogoDTO jogo);
    void atualizar(JogoDTO jogo);
    void excluir(int id);
    List<JogoDTO> listar();
    List<JogoDTO> buscarPorTermo(String termo);
}