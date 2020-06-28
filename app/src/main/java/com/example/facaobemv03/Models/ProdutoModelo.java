package com.example.facaobemv03.Models;

public class ProdutoModelo {

    private long id = -1;
    private String nomeProduto;
    private long quantidade;
    private long idDoador = -1;
    private String doador = null;

    private String marcaProduto;
    private String Descricao;

    public String getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(String marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getDoador() {
        return doador;
    }

    public void setDoador(String doador) {
        this.doador = doador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public long getIdDoador() {
        return idDoador;
    }

    public void setIdDoador(long idDoador) {
        this.idDoador = idDoador;
    }
}
