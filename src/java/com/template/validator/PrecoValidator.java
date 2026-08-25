package com.template.validator;

public class PrecoValidator implements Validator<String> {
    private final String valor;
    private String mensagemErro = "O campo Preço é inválido.";

    public PrecoValidator(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (valor == null || valor.trim().isEmpty()) {
            mensagemErro = "O campo Preço deve ser preenchido.";
            return false;
        }
        try {
            double preco = Double.parseDouble(valor.replace(",", "."));
            if (preco < 0) {
                mensagemErro = "O preço do jogo não pode ser negativo.";
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            mensagemErro = "Digite um valor numérico válido para o preço (ex: 150.00).";
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public String getValor() {
        return valor;
    }

    public double getPrecoConvertido() {
        return Double.parseDouble(valor.replace(",", "."));
    }
}