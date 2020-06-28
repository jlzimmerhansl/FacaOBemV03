package com.example.facaobemv03.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableCentrosRecebimento implements BaseColumns {
    public static final String NOME_TABELA = "centroRecebimento";
    public static final String CAMPO_NOME_CENTRO = "instituicao";
    public static final String CAMPO_ENDERECO = "endereco";
    public static final String CAMPO_CIDADE = "cidade";
    public static final String CAMPO_CEP = "cep";

    public static final String[] TODOS_CAMPOS = new String[]{_ID, CAMPO_NOME_CENTRO, CAMPO_ENDERECO, CAMPO_CIDADE, CAMPO_CEP};

    private final SQLiteDatabase db;

    public BdTableCentrosRecebimento(SQLiteDatabase db){
        this.db = db;
    }

    public void cria(){
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME_CENTRO + " TEXT NOT NULL," +
                        CAMPO_ENDERECO + " TEXT NOT NULL," +
                        CAMPO_CIDADE + " TEXT NOT NULL," +
                        CAMPO_CEP+ " TEXT NOT NULL" +
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
