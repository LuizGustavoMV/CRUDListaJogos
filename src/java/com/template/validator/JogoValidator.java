package com.template.validator;

public class JogoValidator {

    public static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do jogo é obrigatório.");
        }
    }

    public static double parsePreco(String precoStr) {
        if (precoStr == null || precoStr.isBlank()) {
            throw new IllegalArgumentException("O preço é obrigatório.");
        }
        try {
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            if (preco < 0) {
                throw new IllegalArgumentException("O preço não pode ser negativo.");
            }
            return preco;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Preço inválido.");
        }
    }

    public static int parseId(String idStr) {
        if (idStr == null || idStr.isBlank()) {
            throw new IllegalArgumentException("Selecione um jogo na tabela.");
        }
        try {
            return Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }
}