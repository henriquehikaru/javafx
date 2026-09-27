package com.template.model.dto;

public class ProdutoDTO {
    private int id_produto;
    private String descricao;
    private String categoria;
    private double preco;

    public int getId_produto() {
        return id_produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
