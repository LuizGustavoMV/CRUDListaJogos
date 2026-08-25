package com.template.validator;

public class JogoValidator {

    // Valida todos os campos do formulário de uma só vez
    public static void validarCampos(String nome, String plataforma, String precoStr) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do jogo é obrigatório.");
        }

        if (plataforma == null || plataforma.trim().isEmpty()) {
            throw new IllegalArgumentException("A plataforma é obrigatória.");
        }

        if (precoStr == null || precoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("O preço é obrigatório.");
        }

        try {
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            if (preco < 0) {
                throw new IllegalArgumentException("O preço não pode ser negativo.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O preço informado é inválido. Digite um valor numérico.");
        }
    }

    // Valida e converte o campo ID
    public static int validarEConverterId(String idStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione um jogo na tabela para prosseguir.");
        }
        try {
            return Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O ID do jogo é inválido.");
        }
    }
}