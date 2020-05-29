package com.example.facaobemv03;

import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.database.BdFacaOBemOpenHelper;
import com.example.facaobemv03.database.BdTableDoador;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
}
