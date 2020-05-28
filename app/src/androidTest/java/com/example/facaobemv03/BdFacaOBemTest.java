package com.example.facaobemv03;

import android.content.Context;
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


    @Test
    public void consegueInserirDoador(){
        Context appContext = getTargetContext();

        BdFacaOBemOpenHelper openHelper = new BdFacaOBemOpenHelper(appContext);

        SQLiteDatabase bdFacaOBem = openHelper.getWritableDatabase();


       insereDoador(bdFacaOBem, "Juliana", "22/05/2020", "jlzimmerhansl@gmail.com", "925456791");
       bdFacaOBem.close();

    }
}
