package com.template.validator;

public interface IJogoValidator {
    boolean validarCampos(String nome, String plataforma, String precoStr);
    int validarEConverterId(String idStr);
}