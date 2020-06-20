package com.example.facaobemv03;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;

public class Doador extends AppCompatActivity {
    private Fragment fragmentActual = null;
    private int menuActual = R.menu.menu_lista_doadores;

    public void setFragmentActual(Fragment fragmentActual){
        this.fragmentActual = fragmentActual;
        invalidateOptionsMenu();
    }

    public void setMenuActual(int menuActual){
        if(menuActual != this.menuActual){
            this.menuActual = menuActual;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuActual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_ListaDoadoresFragment_to_AdicionaDoadoresFragment){
            ((ListaDoadoresFragment) fragmentActual).novoDoador();
            return true;
        }
        else if(menuActual == R.menu.menu_lista_doadores){
            if(processaOpcosMenuListaLivros(id)) return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean processaOpcosMenuListaLivros(int id){
        ListaDoadoresFragment listaDoadoresFragment = (ListaDoadoresFragment) fragmentActual;
        if(id == R.id.action_ListaDoadoresFragment_to_AdicionaDoadoresFragment){
            listaDoadoresFragment.novoDoador();
        }
        else if(id == R.id.action_ListaDoadoresFragment_to_alteraDoadoresFragment){
            listaDoadoresFragment.alteraDoador();
            return true;
        }
        else if(id == R.id.action_deletar_doador){
            return true;
        }
        return false;
    }
}
