package com.example.facaobemv03.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableProduto implements BaseColumns {
    public SQLiteDatabase db;
    public static final String NOME_TABELA = "produtos";
    public static final String NOME_PRODUTO = "nomeProduto";
    public static final String QUANTIDADE_PRODUTO = "qtdProduto";
    public static final String DOADOR_ID = "doador";

    public static final String[] TODOS_CAMPOS = new String[]{_ID, NOME_PRODUTO, QUANTIDADE_PRODUTO, DOADOR_ID};

    public BdTableProduto(SQLiteDatabase db){
        this.db = db;
    }

    public void cria(){
        db.execSQL(
            "CREATE TABLE " + NOME_TABELA + "(" +
                    _ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NOME_PRODUTO + " TEXT NOT NULL," +
                    QUANTIDADE_PRODUTO + " INTEGER NOT NULL," +
                    DOADOR_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + DOADOR_ID + ") REFERENCES " + BdTableDoador.NOME_TABELA + "(" + BdTableDoador._ID + ")" +
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
