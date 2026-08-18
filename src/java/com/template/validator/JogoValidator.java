package com.template.validator;

import com.template.exception.ValidationException;

public class JogoValidator {

    public static void validarCamposObrigatorios(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ValidationException("O Nome/Título do jogo é obrigatório.");
        }
    }

    public static double validarEConverterPreco(String precoStr) {
        if (precoStr == null || precoStr.trim().isEmpty()) {
            throw new ValidationException("O campo Preço é obrigatório.");
        }
        try {
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            if (preco < 0) {
                throw new ValidationException("O preço não pode ser negativo.");
            }
            return preco;
        } catch (NumberFormatException e) {
            throw new ValidationException("Formato de preço inválido. Digite um número válido.");
        }
    }

    public static int validarEConverterId(String idStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new ValidationException("Selecione um jogo na tabela para prosseguir.");
        }
        try {
            return Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("ID do jogo inválido.");
        }
    }
}