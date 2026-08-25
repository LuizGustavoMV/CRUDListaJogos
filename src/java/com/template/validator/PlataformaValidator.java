package com.template.validator;

public class PlataformaValidator implements Validator<String> {
    private final String valor;
    private String mensagemErro;

    public PlataformaValidator(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (valor == null || valor.trim().isEmpty()) {
            mensagemErro = "O campo Plataforma deve ser preenchido.";
            return false;
        }
        if (valor.trim().length() < 2) {
            mensagemErro = "A Plataforma deve ter pelo menos 2 caracteres (ex: PC, PS5, Xbox).";
            return false;
        }
        return true;
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public String getValor() {
        return valor;
    }
}