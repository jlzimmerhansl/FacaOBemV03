package com.example.facaobemv03;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.facaobemv03.Models.CentroRecebimentoModelo;
import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.Models.ProdutoDetalheModelo;
import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdTableCentrosRecebimento;
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

    public static DoadorModelo cursorToDoador(Cursor cursor){
        DoadorModelo doadorModelo = new DoadorModelo();

        doadorModelo.setId(cursor.getInt(cursor.getColumnIndex(BdTableDoador._ID)));
        doadorModelo.setNomeDoador(cursor.getString(cursor.getColumnIndex(BdTableDoador.CAMPO_NOME_DOADOR)));
        doadorModelo.setDataDoacao(cursor.getString(cursor.getColumnIndex(BdTableDoador.CAMPO_DATA)));
        doadorModelo.setEmailDoador(cursor.getString(cursor.getColumnIndex(BdTableDoador.CAMPO_EMAIL)));
        doadorModelo.setTelefoneDoador(cursor.getString(cursor.getColumnIndex(BdTableDoador.CAMPO_TELEFONE)));

        return doadorModelo;
    }

    public static ContentValues produtoToContentValues(ProdutoModelo produtoModelo){
        ContentValues valores = new ContentValues();

        valores.put(BdTableProduto.NOME_PRODUTO, produtoModelo.getNomeProduto());
        valores.put(BdTableProduto.QUANTIDADE_PRODUTO, produtoModelo.getQuantidade());
        valores.put(BdTableProduto.CAMPO_MARCA, produtoModelo.getMarcaProduto());
        valores.put(BdTableProduto.CAMPO_DESCRICAO, produtoModelo.getDescricao());
        valores.put(BdTableProduto.CAMPO_ID_DOADOR, produtoModelo.getIdDoador());

        return valores;
    }

    public static  ProdutoModelo contentValuesToProduto(ContentValues valores){
        ProdutoModelo produtoModelo = new ProdutoModelo();

        produtoModelo.setId((valores.getAsLong(BdTableProduto._ID)));
        produtoModelo.setNomeProduto(valores.getAsString(BdTableProduto.NOME_PRODUTO));
        produtoModelo.setQuantidade(valores.getAsLong(BdTableProduto.QUANTIDADE_PRODUTO));
        produtoModelo.setMarcaProduto(valores.getAsString(BdTableProduto.CAMPO_MARCA));
        produtoModelo.setDescricao(valores.getAsString(BdTableProduto.CAMPO_DESCRICAO));
        produtoModelo.setIdDoador(valores.getAsLong(BdTableProduto.CAMPO_ID_DOADOR));
        produtoModelo.setDoador(valores.getAsString(BdTableProduto.DOADOR));

        return produtoModelo;
    }

    public static ProdutoModelo cursorToProduto(Cursor cursor){
        ProdutoModelo produtoModelo = new ProdutoModelo();

        produtoModelo.setId(cursor.getLong(cursor.getColumnIndex(BdTableProduto._ID)));
        produtoModelo.setNomeProduto((cursor.getString(cursor.getColumnIndex(BdTableProduto.NOME_PRODUTO))));
        produtoModelo.setQuantidade(cursor.getInt(cursor.getColumnIndex(BdTableProduto.QUANTIDADE_PRODUTO)));
        produtoModelo.setMarcaProduto((cursor.getString(cursor.getColumnIndex(BdTableProduto.CAMPO_MARCA))));
        produtoModelo.setDescricao((cursor.getString(cursor.getColumnIndex(BdTableProduto.CAMPO_DESCRICAO))));
        produtoModelo.setIdDoador(cursor.getLong(cursor.getColumnIndex(BdTableProduto.CAMPO_ID_DOADOR)));
        produtoModelo.setDoador(cursor.getString(cursor.getColumnIndex(BdTableProduto.DOADOR)));


        return produtoModelo;
    }


    public static ContentValues centroRecebimentoToContentValues(CentroRecebimentoModelo centroRecebimentoModelo){
        ContentValues valores = new ContentValues();

        valores.put(BdTableCentrosRecebimento.CAMPO_NOME_CENTRO, centroRecebimentoModelo.getNomeInstituicao());
        valores.put(BdTableCentrosRecebimento.CAMPO_ENDERECO, centroRecebimentoModelo.getEndereco());
        valores.put(BdTableCentrosRecebimento.CAMPO_CIDADE, centroRecebimentoModelo.getCidade());
        valores.put(BdTableCentrosRecebimento.CAMPO_CEP, centroRecebimentoModelo.getCep());

        return valores;

    }

    public static CentroRecebimentoModelo contentToCentroREcebimento(ContentValues valores){
        CentroRecebimentoModelo centroRecebimentoModelo = new CentroRecebimentoModelo();

        centroRecebimentoModelo.setId(valores.getAsLong(BdTableCentrosRecebimento._ID));
        centroRecebimentoModelo.setNomeInstituicao(valores.getAsString(BdTableCentrosRecebimento.CAMPO_NOME_CENTRO));
        centroRecebimentoModelo.setEndereco(valores.getAsString(BdTableCentrosRecebimento.CAMPO_ENDERECO));
        centroRecebimentoModelo.setCidade(valores.getAsString(BdTableCentrosRecebimento.CAMPO_CIDADE));
        centroRecebimentoModelo.setCep(valores.getAsString(BdTableCentrosRecebimento.CAMPO_CEP));

        return centroRecebimentoModelo;
    }

    public static CentroRecebimentoModelo cursorToPCentroRecebimento(Cursor cursor){
        CentroRecebimentoModelo centroRecebimentoModelo = new CentroRecebimentoModelo();

        centroRecebimentoModelo.setId(cursor.getInt(cursor.getColumnIndex(BdTableCentrosRecebimento._ID)));
        centroRecebimentoModelo.setNomeInstituicao(cursor.getString(cursor.getColumnIndex(BdTableCentrosRecebimento.CAMPO_NOME_CENTRO)));
        centroRecebimentoModelo.setEndereco(cursor.getString(cursor.getColumnIndex(BdTableCentrosRecebimento.CAMPO_ENDERECO)));
        centroRecebimentoModelo.setCidade(cursor.getString(cursor.getColumnIndex(BdTableCentrosRecebimento.CAMPO_CIDADE)));
        centroRecebimentoModelo.setCep(cursor.getString(cursor.getColumnIndex(BdTableCentrosRecebimento.CAMPO_CEP)));

        return centroRecebimentoModelo;
    }

}
