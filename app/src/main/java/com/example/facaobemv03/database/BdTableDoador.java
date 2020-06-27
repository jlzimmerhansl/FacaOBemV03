package com.example.facaobemv03.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

public class BdTableDoador implements BaseColumns {

    public static final String NOME_TABELA = "doador";
    public static final String CAMPO_NOME_DOADOR = "nomedoador";
    public static final String CAMPO_DATA = "datadoacao";
    public static final String CAMPO_EMAIL = "emaildoador";
    public static final String CAMPO_TELEFONE = "telefonedoador";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String CAMPO_NOMEDOADOR_COMPLETO = NOME_TABELA + "." + CAMPO_NOME_DOADOR;

    public static final String[] TODOS_CAMPOS = new String[]{_ID, CAMPO_NOME_DOADOR, CAMPO_DATA, CAMPO_EMAIL, CAMPO_TELEFONE};

    private final SQLiteDatabase db;

    public BdTableDoador(SQLiteDatabase db){
        this.db = db;
    }

    public void cria(){
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME_DOADOR + " TEXT NOT NULL," +
                        CAMPO_DATA + " TEXT NOT NULL," +
                        CAMPO_EMAIL + " TEXT NOT NULL," +
                        CAMPO_TELEFONE + " TEXT NOT NULL" +
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
