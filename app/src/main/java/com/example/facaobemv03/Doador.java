package com.example.facaobemv03;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.Models.ProdutoModelo;

public class Doador extends AppCompatActivity {
    private Fragment fragmentActual = null;
    private int menuActual = R.menu.menu_lista_doadores;
    private Menu menu;
    private DoadorModelo doadorModelo = null;
    private ProdutoModelo produtoModelo;

   public DoadorModelo getDoadorModelo(){
       return doadorModelo;
   }

   public ProdutoModelo getProdutoModelo(){
       return produtoModelo;
   }

    public void setFragmentActual(Fragment fragmentActual){
        this.fragmentActual = fragmentActual;

    }

    public void setMenuActual(int menuActual){
        if(menuActual != this.menuActual){
            this.menuActual = menuActual;
            invalidateOptionsMenu();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void produtoAlterado(ProdutoModelo produtoModelo){
       this.produtoModelo = produtoModelo;
    }

    public void doadorAlterado(DoadorModelo doadorModelo){

        this.doadorModelo = doadorModelo;
        boolean mostraMenuEditarEliminar = (doadorModelo != null);

        menu.findItem(R.id.action_ListaDoadoresFragment_to_alteraDoadoresFragment).setVisible(mostraMenuEditarEliminar);
        menu.findItem(R.id.action_deletar_doador).setVisible(mostraMenuEditarEliminar);
        menu.findItem(R.id.action_ListaDoadores_to_DetalheDoador).setVisible(mostraMenuEditarEliminar);
    }

    public void atualizaOpcoesDeMenuListaProdutos(ProdutoModelo produtoModelo){
       this.produtoModelo = produtoModelo;

        boolean mostraOpcoesMenuEscondidas = (produtoModelo != null);

        menu.findItem(R.id.action_alterarProduto).setVisible(mostraOpcoesMenuEscondidas);
        menu.findItem(R.id.action_deletarProduto).setVisible(mostraOpcoesMenuEscondidas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuActual, menu);

       this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(menuActual == R.menu.menu_lista_doadores){
            if(processaOpcoesMenuListaDoadores(id)) return true;
        }
        else if(menuActual == R.menu.menu_cadastrodoador){
            if(processaOpcoesMenuAdicionarDoadores(id)) return true;
        }
        else if(menuActual == R.menu.menu_alterar_doador){
            if(processaOpcoesMenuAlterarDoadores(id)) return true;
        }
        else if(menuActual == R.menu.menu_deletardoador){
            if(processaOpcoesMenuDeletarDoadores(id)) return true;
        }
        else if(menuActual == R.menu.menu_lista_produtos){
            if(processaOpcoesMenuListaProdutos(id)) return true;
        }
        else if(menuActual == R.menu.menu_inserir_produto){
            if(processaOpcoesMenuInserirProdutos(id)) return true;
        }
        else if(menuActual == R.menu.menu_alterar_produto){
            if(processaOpcoesMenuAlterarProdutos(id)) return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean processaOpcoesMenuAlterarProdutos(int id) {
       AlterarProdutoFragment alterarProdutoFragment = (AlterarProdutoFragment) fragmentActual;
        if(id == R.id.action_guardarAlterarProduto){
            alterarProdutoFragment.alterarPrduto();
        }
        else if(id == R.id.action_cancelar_inserirProduto){
            alterarProdutoFragment.cancelarAlterarProduto();
        }
       return false;
    }

    private boolean processaOpcoesMenuInserirProdutos(int id) {
       InserirProdutosFragment inserirProdutosFragment = (InserirProdutosFragment) fragmentActual;
       if(id == R.id.action_guardar_produto){
           inserirProdutosFragment.cadastrarProduto();
       }
       else if(id == R.id.action_cancelar_inserirProduto){
           inserirProdutosFragment.cadastrarProduto();
       }
       return false;
    }

    private boolean processaOpcoesMenuListaProdutos(int id) {
        LIstaProdutosFragment lIstaProdutosFragment = (LIstaProdutosFragment) fragmentActual;
       if(id == R.id.action_inserirProduto){
            lIstaProdutosFragment.CadastrarProduto();
       }
       else if(id == R.id.action_alterarProduto){
           lIstaProdutosFragment.AlterarProduto();
       }
       else if(id == R.id.action_deletarProduto){
           lIstaProdutosFragment.deletarProduto();
       }
       else if(id == R.id.action_cancelarListaProdutos){
           lIstaProdutosFragment.cancelarListaProdutos();
       }
       return false;
    }

    private boolean processaOpcoesMenuDeletarDoadores(int id) {
       EliminaDoadoresFragment eliminaDoadoresFragment = (EliminaDoadoresFragment) fragmentActual;

       if(id == R.id.action_eliminaDoadoresFragment_to_ListaDoadoresFragment){
           eliminaDoadoresFragment.cancelarDeletarDoador();
           return true;
       }

       return false;
    }

    private boolean processaOpcoesMenuAlterarDoadores(int id) {
        alteraDoadoresFragment alteraDoadoresFragment = (alteraDoadoresFragment) fragmentActual;
        if(id == R.id.action_guardarAlterarDoador){
            alteraDoadoresFragment.cadastrarAlteraDoador();
            return true;
        }
        else if(id == R.id.action_cancelarDoadorAlterar){
            alteraDoadoresFragment.cancelar();
            return true;
        }


        return false;
    }

    private boolean processaOpcoesMenuAdicionarDoadores(int id) {
        AdicionaDoadoresFragment adicionaDoadoresFragment = (AdicionaDoadoresFragment) fragmentActual;
        if(id == R.id.action_guardar_doador){
            adicionaDoadoresFragment.cadastrarDoador();
            return true;
        }
        else if(id == R.id.action_menu_cancelar){
            adicionaDoadoresFragment.cacelarCadastro();
            return true;
        }

        return false;
    }

    public boolean processaOpcoesMenuListaDoadores(int id){
        ListaDoadoresFragment listaDoadoresFragment = (ListaDoadoresFragment) fragmentActual;
        if(id == R.id.action_ListaDoadoresFragment_to_AdicionaDoadoresFragment){
            listaDoadoresFragment.novoDoador();
        }
        else if(id == R.id.action_ListaDoadoresFragment_to_alteraDoadoresFragment){
            listaDoadoresFragment.alteraDoador();
            return true;
        }
        else if(id == R.id.action_deletar_doador){
            listaDoadoresFragment.deletarDoador();
            return true;
        }
        else if(id == R.id.action_ListaDoadores_to_DetalheDoador){
            listaDoadoresFragment.mostraDetalheDoador();
            return true;
        }
        else if(id == R.id.action_VerProdutos){
            listaDoadoresFragment.mostraListaProdutos();
            return true;
        }
        return false;
    }
}
