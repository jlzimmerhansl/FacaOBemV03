package com.example.facaobemv03;

import android.content.ContentValues;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.database.BdTableDoador;

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
}
