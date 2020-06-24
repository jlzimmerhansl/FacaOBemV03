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
        doadorModelo.setNomeDoador("Juliana Satos da Silva");
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
        doadorModelo.setNomeDoador("Janos Moreira Silva");
        doadorModelo.setDataDoacao("10/05/2020");
        doadorModelo.setEmailDoador("contato2@teste.com.br");
        doadorModelo.setTelefoneDoador("343543756");
        long idDoador4 = tableDoador.insert(Converte.doadorToContentValue(doadorModelo));

        BdTableProduto tableProduto = new BdTableProduto(db);

        ProdutoModelo produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Luva");
        produtoModelo.setQuantidade(90);
        produtoModelo.setIdDoador(idDoador);
        long idProduto1 = tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Alcool");
        produtoModelo.setQuantidade(80);
        produtoModelo.setIdDoador(idDoador2);
        long idProduto2 = tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Mascara");
        produtoModelo.setQuantidade(200);
        produtoModelo.setIdDoador(idDoador3);
        long idProduto3 = tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto("Shield");
        produtoModelo.setQuantidade(150);
        produtoModelo.setIdDoador(idDoador4);
        long idProduto4 = tableProduto.insert(Converte.produtoToContentValues(produtoModelo));

        BdTableProdutoDetalhe tableProdutoDetalhe = new BdTableProdutoDetalhe(db);

        ProdutoDetalheModelo produtoDetalheModelo = new ProdutoDetalheModelo();
        produtoDetalheModelo.setMarcaProduto("Clean Line");
        produtoDetalheModelo.setDescricao("Melhor marca para eses produtos, direcionar aos hospitais");
        produtoDetalheModelo.setIdProduto(idProduto1);
        tableProdutoDetalhe.insert(Converte.produtoDetalheToContentValues(produtoDetalheModelo));

        produtoDetalheModelo = new ProdutoDetalheModelo();
        produtoDetalheModelo.setMarcaProduto("Gray Line");
        produtoDetalheModelo.setDescricao("Direcionar para as pessoas com necessidade");
        produtoDetalheModelo.setIdProduto(idProduto2);
        tableProdutoDetalhe.insert(Converte.produtoDetalheToContentValues(produtoDetalheModelo));

        produtoDetalheModelo = new ProdutoDetalheModelo();
        produtoDetalheModelo.setMarcaProduto("Center Care");
        produtoDetalheModelo.setDescricao("Guardar no estoque");
        produtoDetalheModelo.setIdProduto(idProduto4);
        tableProdutoDetalhe.insert(Converte.produtoDetalheToContentValues(produtoDetalheModelo));

        produtoDetalheModelo = new ProdutoDetalheModelo();
        produtoDetalheModelo.setMarcaProduto("Centro do Amor");
        produtoDetalheModelo.setDescricao("Guardar no setor D do estoque");
        produtoDetalheModelo.setIdProduto(idProduto4);
        tableProdutoDetalhe.insert(Converte.produtoDetalheToContentValues(produtoDetalheModelo));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
