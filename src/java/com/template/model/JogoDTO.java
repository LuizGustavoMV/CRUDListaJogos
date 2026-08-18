package com.template.model;

public class JogoDTO {
    private int id;
    private String titulo;
    private String genero;
    private String plataforma;
    private double preco;


    public JogoDTO() {}

    public JogoDTO(int id, String titulo, String genero, String plataforma, double preco) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.plataforma = plataforma;
        this.preco = preco;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String toString() {
        return String.format("ID: %d | Título: %-30s | Gênero: %-15s | Plataforma: %-15s | Preço: R$ %.2f",
                id, titulo, genero, plataforma, preco);
    }
}