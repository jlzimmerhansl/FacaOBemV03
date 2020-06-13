package com.example.facaobemv03.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdFacaOBemOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "FacaOBem.db";
    public static final int VERSAO_BASE_DADOS = 1;

    public BdFacaOBemOpenHelper(@Nullable Context context){
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        BdTableDoador tableDoador =  new BdTableDoador(database);
        tableDoador.cria();

        BdTableProduto tableProduto = new BdTableProduto(database);
        tableProduto.cria();

        BdTableProdutoDetalhe tableProdutoDetalhe = new BdTableProdutoDetalhe(database);
        tableProdutoDetalhe.cria();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
