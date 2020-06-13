package com.example.facaobemv03;

import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.Models.ProdutoDetalheModelo;
import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdFacaOBemOpenHelper;
import com.example.facaobemv03.database.BdTableDoador;
import com.example.facaobemv03.database.BdTableProduto;
import com.example.facaobemv03.database.BdTableProdutoDetalhe;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdFacaOBemTest {

    @Before
    @After
    public void apagarBaseDeDados(){
        getTargetContext().deleteDatabase(BdFacaOBemOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void consegueAbrirBaseDados(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper =  new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bancoDados = openHelper.getReadableDatabase();

        assertTrue(bancoDados.isOpen());
        bancoDados.close();
    }


    private Context getTargetContext() {
        // Context of the app under test.
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    private long insereDoador(SQLiteDatabase bdFacaOBem, String nomeDoador, String dataDoacao, String emailDoador, String telefoneDoador){

        DoadorModelo doadorModelo = new DoadorModelo();

        doadorModelo.setNomeDoador(nomeDoador);
        doadorModelo.setDataDoacao(dataDoacao);
        doadorModelo.setEmailDoador(emailDoador);
        doadorModelo.setTelefoneDoador(telefoneDoador);


        BdTableDoador tableDoador = new BdTableDoador(bdFacaOBem);
        long id = tableDoador.insert(Converte.doadorToContentValue(doadorModelo));

        assertNotEquals(-1, id);
        return id;
    }


    public long insereDoadorModelo(BdTableDoador tableDoador, DoadorModelo doadorModelo){
        long id = tableDoador.insert(Converte.doadorToContentValue(doadorModelo));

        assertNotEquals(-1, id);

        return id;
    }

    private long insereDoadorNome(BdTableDoador tableDoador, String nomeDoador, String dataDoacao, String emailDoador, String telefoneDoador){
        DoadorModelo doadorModelo = new DoadorModelo();
        doadorModelo.setNomeDoador(nomeDoador);
        doadorModelo.setDataDoacao(dataDoacao);
        doadorModelo.setEmailDoador(emailDoador);
        doadorModelo.setTelefoneDoador(telefoneDoador);

        return insereDoadorModelo(tableDoador, doadorModelo);
    }

    private long insereProduto(SQLiteDatabase bdFacaOBem, String nomeProduto, long qtdProduto,  String nomeDoador, String dataDoacao, String emailDoador, String telefoneDoador){
        BdTableDoador tableDoador = new BdTableDoador(bdFacaOBem);
        long idDoador = insereDoadorNome(tableDoador, nomeDoador, dataDoacao, emailDoador, telefoneDoador);

        ProdutoModelo produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto(nomeProduto);
        produtoModelo.setQuantidade(qtdProduto);
        produtoModelo.setIdDoador(idDoador);

        BdTableProduto tableProduto = new BdTableProduto(bdFacaOBem);
        long id = tableProduto.insert((Converte.produtoToContentValues(produtoModelo)));
        assertNotEquals(-1, id);

        return id;
    }

    private long inserProdutoTabela(BdTableDoador tableDoador, BdTableProduto tableProduto, String nomeProduto, long qtdProduto,  String nomeDoador, String dataDoacao, String emailDoador, String telefoneDoador){

        long idDoador = insereDoadorNome(tableDoador, nomeDoador, dataDoacao, emailDoador, telefoneDoador);

        ProdutoModelo produtoModelo = new ProdutoModelo();
        produtoModelo.setNomeProduto(nomeProduto);
        produtoModelo.setQuantidade(qtdProduto);
        produtoModelo.setIdDoador(idDoador);


        long id = tableProduto.insert((Converte.produtoToContentValues(produtoModelo)));
        assertNotEquals(-1, id);

        return id;
    }

    private long insereProdutoDetalhe(SQLiteDatabase bdFacaOBem, String marcaProduto, String descricaoProduto, String nomeProduto, long qtdProduto,String nomeDoador, String dataDoacao, String emailDoador, String telefoneDoador){
        BdTableDoador tableDoador = new BdTableDoador(bdFacaOBem);
        BdTableProduto tableProduto = new BdTableProduto(bdFacaOBem);

        long idProdutoDetalhe = inserProdutoTabela(tableDoador, tableProduto, nomeProduto, qtdProduto, nomeDoador, dataDoacao, emailDoador, telefoneDoador);

        ProdutoDetalheModelo produtoDetalheModelo = new ProdutoDetalheModelo();

        produtoDetalheModelo.setMarcaProduto(marcaProduto);
        produtoDetalheModelo.setDescricao(descricaoProduto);
        produtoDetalheModelo.setIdProduto(idProdutoDetalhe);

        BdTableProdutoDetalhe tableProdutoDetalhe = new BdTableProdutoDetalhe(bdFacaOBem);
        long id = tableProdutoDetalhe.insert(Converte.produtoDetalheToContentValues(produtoDetalheModelo));
        assertNotEquals(-1, id);

        return id;
    }


    @Test
    public void consegueInserirDoador(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);

        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

       insereDoador(bdFacaOBem, "Juliana", "22/05/2020", "jlzimmerhansl@gmail.com", "925456791");
       bdFacaOBem.close();

    }

    @Test
    public void consegueLerDoador(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);

        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

        BdTableDoador tableDoador = new BdTableDoador(bdFacaOBem);

        Cursor cursor = tableDoador.query(BdTableDoador.TODOS_CAMPOS, null, null, null, null, null);
        int registros = cursor.getCount();
        cursor.close();

        insereDoador(bdFacaOBem, "Sonia", "23/04/2020", "slzimmerhansl@mail.com", "985311636");

        cursor = tableDoador.query(BdTableDoador.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registros + 1, cursor.getCount());
        cursor.close();
        bdFacaOBem.close();

        }

    @Test
    public void consegueAlterarDoador(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();
        BdTableDoador tableDoador = new BdTableDoador(bdFacaOBem);

        DoadorModelo doadorModelo = new DoadorModelo();
        doadorModelo.setNomeDoador("Jano");
        doadorModelo.setDataDoacao("30/05/2020");
        doadorModelo.setEmailDoador("janoszjunior@gmail.com");
        doadorModelo.setTelefoneDoador("985493161");

        long id = insereDoadorModelo(tableDoador, doadorModelo);
        assertNotEquals(-1, id);

        doadorModelo.setNomeDoador("Janos");
        doadorModelo.setDataDoacao("30/05/2020");
        doadorModelo.setEmailDoador("janoszjunior@gmail.com");
        doadorModelo.setTelefoneDoador("985493161");
        int resgistrosAlterados = tableDoador.update(Converte.doadorToContentValue(doadorModelo), BdTableDoador._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, resgistrosAlterados);
        bdFacaOBem.close();
    }

    @Test
    public void consegueApagarDoador(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();
        BdTableDoador tableDoador = new BdTableDoador(bdFacaOBem);

        long id = insereDoador(bdFacaOBem, "Maria", "30/04/2020", "maria@mail.com", "985311636");
        assertNotEquals(-1, id);

        int registrosApagados = tableDoador.delete(BdTableDoador._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registrosApagados);
        bdFacaOBem.close();

    }

    @Test
    public void consegueInserirProduto(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

        insereProduto(bdFacaOBem, "Alcool", 30, "Janos", "30/05/2020", "contato@gmail.com", "925456888");
        bdFacaOBem.close();
    }

    @Test
    public void consegueLerProduto(){
        Context appContext = getTargetContext();
        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

        BdTableProduto tableProduto = new BdTableProduto(bdFacaOBem);

        Cursor cursor = tableProduto.query(BdTableProduto.TODOS_CAMPOS, null, null, null, null, null);
        int registros = cursor.getCount();
        cursor.close();

        insereProduto(bdFacaOBem, "Mascara", 40, "Maria", "15/05/2020", "contato@gmail.com", "925456654");

        cursor = tableProduto.query(BdTableProduto.TODOS_CAMPOS,null, null, null, null, null);

        assertEquals(registros + 1, cursor.getCount());
        cursor.close();

        bdFacaOBem.close();
    }

    @Test
    public void consegueAlterarProduto(){
        Context appContext = getTargetContext();
        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

        long idProduto = insereProduto(bdFacaOBem, "Luva", 150, "João", "09/05/2020", "joao@gmail.com", "975346777");

        BdTableProduto tableProduto = new BdTableProduto(bdFacaOBem);
        Cursor cursorProdutos = tableProduto.query(BdTableProduto.TODOS_CAMPOS, BdTableProduto._ID + "=?", new String[]{String.valueOf(idProduto)}, null, null, null);
        assertEquals(1, cursorProdutos.getCount());

        assertTrue(cursorProdutos.moveToNext());

        ProdutoModelo produtoModelo = Converte.cursorToProduto(cursorProdutos);

        assertEquals("Luva", produtoModelo.getNomeProduto());
        cursorProdutos.close();

        produtoModelo.setNomeProduto("LuvaSilicone");

        int registrosAlterados = tableProduto.update(Converte.produtoToContentValues(produtoModelo), BdTableProduto._ID + "=?", new String[]{String.valueOf(produtoModelo.getId())});
        assertEquals(1, registrosAlterados);
        bdFacaOBem.close();
    }

    @Test
    public void  consegueApagarProduto(){
        Context appContext = getTargetContext();
        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

        long idProduto = insereProduto(bdFacaOBem, "Cesta Básica", 90, "Suzana", "10/05/2020", "suzana@gmail.com", "975346755");

        BdTableProduto tableProduto = new BdTableProduto(bdFacaOBem);

        int registrosApagados = tableProduto.delete(BdTableProduto._ID + "= ?", new String[]{String.valueOf(idProduto)});
        assertEquals(1, registrosApagados);
        bdFacaOBem.close();
    }

    @Test
    public void consegueInserirProdutoDetalhe(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

        insereProdutoDetalhe(bdFacaOBem, "LightClean", "Recebemos 30 unidades", "Luva", 40, "Fernada", "04/05/2020", "contato@light.com.br", "867594665");

        bdFacaOBem.close();
    }
    @Test
    public void consegueAlterarProdutoDetahe(){
        Context appContext = getTargetContext();
        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);
        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();

        long idProdutoDetalhe = insereProdutoDetalhe(bdFacaOBem, "Cien", "Esse produto deve ser direcionado ao centro de ajuda", "Alcool", 200, "Cintia", "13/06/2020", "contato@cien.com.br", "867599886");

        BdTableProdutoDetalhe tableProdutoDetalhe = new BdTableProdutoDetalhe(bdFacaOBem);
        Cursor cursorProdutoDetalhe = tableProdutoDetalhe.query(BdTableProdutoDetalhe.TODOS_CAMPOS, BdTableProdutoDetalhe._ID + " =?", new String[]{String.valueOf(idProdutoDetalhe)}, null, null, null);
        assertEquals(1, cursorProdutoDetalhe.getCount());

        assertTrue(cursorProdutoDetalhe.moveToNext());

        ProdutoDetalheModelo produtoDetalheModelo = Converte.cursorToProdutoDetalhe(cursorProdutoDetalhe);
        assertEquals("Cien", produtoDetalheModelo.getMarcaProduto());
        cursorProdutoDetalhe.close();

        produtoDetalheModelo.setMarcaProduto("Cien LTDA");

        int registrosAlterados = tableProdutoDetalhe.update(Converte.produtoDetalheToContentValues(produtoDetalheModelo), BdTableProdutoDetalhe._ID + " =?", new String[]{String.valueOf(produtoDetalheModelo.getId())});
        assertEquals(1, registrosAlterados);
        bdFacaOBem.close();
    }
}
