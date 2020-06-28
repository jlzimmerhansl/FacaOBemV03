package com.example.facaobemv03.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.facaobemv03.Converte;
import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.Models.ProdutoDetalheModelo;
import com.example.facaobemv03.Models.ProdutoModelo;

import androidx.annotation.Nullable;

public class BdFacaOBemOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "FacaOBem.db";
    public static final int VERSAO_BASE_DADOS = 1;

    private static final boolean DESENVOLVIMENTO = true;

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

        if(DESENVOLVIMENTO)
            seedData(database);
    }

    private void seedData(SQLiteDatabase db) {
        BdTableDoador tableDoador = new BdTableDoador(db);

        DoadorModelo doadorModelo = new DoadorModelo();
        doadorModelo.setNomeDoador("Juliana Santos da Silva");
        doadorModelo.setDataDoacao("22/05/2020");
        doadorModelo.setEmailDoador("contato@teste.com.br");
        doadorModelo.setTelefoneDoador("987543756");
        long idDoador = tableDoador.insert(Converte.doadorToContentValue(doadorModelo));

        doadorModelo = new DoadorModelo();
        doadorModelo.setNomeDoador("Sonia Rodrigues Santos");
        doadorModelo.setDataDoacao("10/05/2020");
        doadorModelo.setEmailDoador("contato2@teste.com.br");
        doadorModelo.setTelefoneDoador("09874366");
        long idDoador2 = tableDoador.insert(Converte.doadorToContentValue(doadorModelo));

        doadorModelo = new DoadorModelo();
        doadorModelo.setNomeDoador("Priscila do Nascimento Carvalho");
        doadorModelo.setDataDoacao("13/05/2020");
        doadorModelo.setEmailDoador("contato3@teste.com.br");
        doadorModelo.setTelefoneDoador("987543444");
        long idDoador3 = tableDoador.insert(Converte.doadorToContentValue(doadorModelo));

        doadorModelo = new DoadorModelo();
        doadorModelo.setNomeDoador("Janos Zimmerhansl");
        doadorModelo.setDataDoacao("10/05/2020");
        doadorModelo.setEmailDoador("contato2@teste.com.br");
        doadorModelo.setTelefoneDoador("343543756");
        long idDoador4 = tableDoador.insert(Converte.doadorToContentValue(doadorModelo));

        BdTableProduto tableProduto = new BdTableProduto(db);

        ProdutoModelo produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Luva");
        produtoModelo.setQuantidade(90);
        produtoModelo.setMarcaProduto("Up Clean");
        produtoModelo.setDescricao("Suprimentos Hospitalares");
        produtoModelo.setIdDoador(idDoador);
        tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Alimentos não - pereciveis");
        produtoModelo.setQuantidade(300);
        produtoModelo.setMarcaProduto("Flower Beautiful");
        produtoModelo.setDescricao("Estoque imediato");
        produtoModelo.setIdDoador(idDoador);
        tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("teste");
        produtoModelo.setQuantidade(300);
        produtoModelo.setMarcaProduto("Clean Line");
        produtoModelo.setDescricao("Estoque imediato no galpão 9");
        produtoModelo.setIdDoador(idDoador);
        tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Alcool");
        produtoModelo.setQuantidade(80);
        produtoModelo.setMarcaProduto("Terra Saudavel");
        produtoModelo.setDescricao("Estoque imediato no galpão 7");
        produtoModelo.setIdDoador(idDoador2);
        tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Mascara");
        produtoModelo.setQuantidade(200);
        produtoModelo.setMarcaProduto("Easy Peace");
        produtoModelo.setDescricao("Galpão 3");
        produtoModelo.setIdDoador(idDoador3);
        tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Shield");
        produtoModelo.setQuantidade(150);
        produtoModelo.setMarcaProduto("Clean Easy");
        produtoModelo.setDescricao("Galpão 5");
        produtoModelo.setIdDoador(idDoador4);
        tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Cesta Basica");
        produtoModelo.setQuantidade(70);
        produtoModelo.setMarcaProduto("Clean Line");
        produtoModelo.setDescricao("Estoque imediato");
        produtoModelo.setIdDoador(idDoador4);
        tableProduto.insert(Converte.produtoToContentValues(produtoModelo));


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
