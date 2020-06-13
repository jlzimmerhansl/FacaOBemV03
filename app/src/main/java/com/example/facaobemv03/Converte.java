package com.example.facaobemv03;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.Models.ProdutoDetalheModelo;
import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdTableDoador;
import com.example.facaobemv03.database.BdTableProduto;
import com.example.facaobemv03.database.BdTableProdutoDetalhe;

public class Converte {

    //Converter um doador em Content values
    public static ContentValues doadorToContentValue(DoadorModelo doador){
        ContentValues valores = new ContentValues();

        valores.put(BdTableDoador.CAMPO_NOME_DOADOR, doador.getNomeDoador());
        valores.put(BdTableDoador.CAMPO_DATA, doador.getDataDoacao());
        valores.put(BdTableDoador.CAMPO_EMAIL, doador.getEmailDoador());
        valores.put(BdTableDoador.CAMPO_TELEFONE, doador.getTelefoneDoador());

        return valores;
    }

    //Converter um Content Values em Doador
    public static DoadorModelo contentValuesToDoador(ContentValues valores){
        DoadorModelo doador = new DoadorModelo();

        doador.setId(valores.getAsLong(BdTableDoador._ID));
        doador.setNomeDoador(valores.getAsString(BdTableDoador.CAMPO_NOME_DOADOR));
        doador.setDataDoacao(valores.getAsString(BdTableDoador.CAMPO_DATA));
        doador.setEmailDoador(valores.getAsString(BdTableDoador.CAMPO_EMAIL));
        doador.setTelefoneDoador(valores.getAsString(BdTableDoador.CAMPO_TELEFONE));

        return doador;
    }

    public static ContentValues produtoToContentValues(ProdutoModelo produtoModelo){
        ContentValues valores = new ContentValues();

        valores.put(BdTableProduto.NOME_PRODUTO, produtoModelo.getNomeProduto());
        valores.put(BdTableProduto.QUANTIDADE_PRODUTO, produtoModelo.getQuantidade());
        valores.put(BdTableProduto.DOADOR_ID, produtoModelo.getIdDoador());

        return valores;
    }

    public static  ProdutoModelo contentValuesToProduto(ContentValues valores){
        ProdutoModelo produtoModelo = new ProdutoModelo();

        produtoModelo.setId((valores.getAsLong(BdTableProduto._ID)));
        produtoModelo.setNomeProduto(valores.getAsString(BdTableProduto.NOME_PRODUTO));
        produtoModelo.setQuantidade(valores.getAsLong(BdTableProduto.QUANTIDADE_PRODUTO));
        produtoModelo.setIdDoador(valores.getAsLong(BdTableProduto.DOADOR_ID));

        return produtoModelo;
    }

    public static ProdutoModelo cursorToProduto(Cursor cursor){
        ProdutoModelo produtoModelo = new ProdutoModelo();

        produtoModelo.setId(cursor.getInt(cursor.getColumnIndex(BdTableProduto._ID)));
        produtoModelo.setNomeProduto((cursor.getString(cursor.getColumnIndex(BdTableProduto.NOME_PRODUTO))));
        produtoModelo.setQuantidade(cursor.getInt(cursor.getColumnIndex(BdTableProduto.QUANTIDADE_PRODUTO)));
        produtoModelo.setIdDoador(cursor.getInt(cursor.getColumnIndex(BdTableProduto.DOADOR_ID)));

        return produtoModelo;
    }

    public static ContentValues produtoDetalheToContentValues(ProdutoDetalheModelo produtoDetalheModelo){
        ContentValues valores = new ContentValues();

        valores.put(BdTableProdutoDetalhe.CAMPO_MARCA, produtoDetalheModelo.getMarcaProduto());
        valores.put(BdTableProdutoDetalhe.CAMPO_DESCRICAO, produtoDetalheModelo.getDescricao());
        valores.put(BdTableProdutoDetalhe.CAMPO_ID_PRODUTO, produtoDetalheModelo.getIdProduto());

        return valores;

    }

    public static ProdutoDetalheModelo contentToProdutoDetalhe(ContentValues valores){
        ProdutoDetalheModelo produtoDetalheModelo = new ProdutoDetalheModelo();

        produtoDetalheModelo.setId(valores.getAsLong(BdTableProdutoDetalhe._ID));
        produtoDetalheModelo.setMarcaProduto(valores.getAsString(BdTableProdutoDetalhe.CAMPO_MARCA));
        produtoDetalheModelo.setDescricao(valores.getAsString(BdTableProdutoDetalhe.CAMPO_DESCRICAO));
        produtoDetalheModelo.setIdProduto(valores.getAsLong(BdTableProdutoDetalhe.CAMPO_ID_PRODUTO));

        return produtoDetalheModelo;
    }

    public static ProdutoDetalheModelo cursorToProdutoDetalhe(Cursor cursor){
        ProdutoDetalheModelo produtoDetalheModelo = new ProdutoDetalheModelo();

        produtoDetalheModelo.setId(cursor.getInt(cursor.getColumnIndex(BdTableProdutoDetalhe._ID)));
        produtoDetalheModelo.setMarcaProduto(cursor.getString(cursor.getColumnIndex(BdTableProdutoDetalhe.CAMPO_MARCA)));
        produtoDetalheModelo.setDescricao(cursor.getString(cursor.getColumnIndex(BdTableProdutoDetalhe.CAMPO_DESCRICAO)));
        produtoDetalheModelo.setIdProduto(cursor.getInt(cursor.getColumnIndex(BdTableProdutoDetalhe.CAMPO_ID_PRODUTO)));

        return produtoDetalheModelo;
    }

}
