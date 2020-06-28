package com.example.facaobemv03.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTableProduto implements BaseColumns {
    public SQLiteDatabase db;
    public static final String NOME_TABELA = "produtos";
    public static final String NOME_PRODUTO = "nomeProduto";
    public static final String QUANTIDADE_PRODUTO = "qtdProduto";
    public static final String DOADOR = "doador";
    public static final String CAMPO_ID_DOADOR = "id_doador";

    public static final String CAMPO_MARCA = "marca";
    public static final String CAMPO_DESCRICAO = "descricao";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String CAMPO_NOMEPRODUTO_COMPLETO = NOME_TABELA + "." + NOME_PRODUTO;
    public static final String CAMPO_QUANTIDADE_COMPLETO = NOME_TABELA + "." + QUANTIDADE_PRODUTO;
    public static final String CAMPO_ID_DOADOR_COMPLETO = NOME_TABELA + "." + CAMPO_ID_DOADOR;
    public static final String CAMPO_MARCA_COMPLETO = NOME_TABELA + "." + CAMPO_MARCA;
    public static final String CAMPO_DESCRICAO_COMPLETO = NOME_TABELA + "." + CAMPO_DESCRICAO;
    public static final String CAMPO_DOADOR_COMPLETO = BdTableDoador.CAMPO_NOMEDOADOR_COMPLETO + " AS " + DOADOR;

    public static final String[] TODOS_CAMPOS = new String[]{
            CAMPO_ID_COMPLETO,
            CAMPO_NOMEPRODUTO_COMPLETO,
            CAMPO_QUANTIDADE_COMPLETO,
            CAMPO_MARCA_COMPLETO,
            CAMPO_DESCRICAO_COMPLETO,
            CAMPO_ID_DOADOR_COMPLETO,
            CAMPO_DOADOR_COMPLETO
    };


    public BdTableProduto(SQLiteDatabase db){
        this.db = db;
    }

    public void cria(){
        db.execSQL(
            "CREATE TABLE " + NOME_TABELA + "(" +
                    _ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NOME_PRODUTO + " TEXT NOT NULL," +
                    QUANTIDADE_PRODUTO + " INTEGER NOT NULL," +
                    CAMPO_MARCA + " TEXT NOT NULL," +
                    CAMPO_DESCRICAO + " TEXT NOT NULL," +
                    CAMPO_ID_DOADOR + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + CAMPO_ID_DOADOR + ") REFERENCES " +
                        BdTableDoador.NOME_TABELA + "(" + BdTableDoador._ID + ")" +
             ")"
        );
    }



    public long insert(ContentValues values){
        return db.insert(NOME_TABELA, null, values );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){

        if(!Arrays.asList(columns).contains(CAMPO_DOADOR_COMPLETO)){
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTableDoador.NOME_TABELA;
        sql += " ON " + CAMPO_ID_DOADOR_COMPLETO + "=" + BdTableDoador.CAMPO_ID_COMPLETO;

        if(selection != null){
            sql += " WHERE " + selection;
        }

        if (groupBy != null) {
            sql += " GROUP BY " + groupBy;

            if (having != null) {
                sql += " HAVING " + having;
            }
        }

        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }

        return db.rawQuery(sql, selectionArgs);

    }



    public int update(ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs){
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
