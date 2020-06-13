package com.example.facaobemv03.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableProdutoDetalhe implements BaseColumns {
    public SQLiteDatabase db;
    public static final String NOME_TABELA = "produtoDetalhe";

    public static final String CAMPO_MARCA = "marca";
    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String CAMPO_ID_PRODUTO = "idProduto";

    public static final String[] TODOS_CAMPOS = new String[]{_ID, CAMPO_MARCA, CAMPO_DESCRICAO, CAMPO_ID_PRODUTO};

    public BdTableProdutoDetalhe(SQLiteDatabase db){
        this.db = db;
    }

    public void cria(){
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_MARCA + " TEXT NOT NULL," +
                        CAMPO_DESCRICAO + " TEXT NOT NULL," +
                        CAMPO_ID_PRODUTO + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + CAMPO_ID_PRODUTO + ") REFERENCES " + BdTableProduto.NOME_TABELA + "(" + BdTableProduto._ID + ")" +
                        ")"
        );
    }

    public long insert(ContentValues values){
        return db.insert(NOME_TABELA, null, values );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs){
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
