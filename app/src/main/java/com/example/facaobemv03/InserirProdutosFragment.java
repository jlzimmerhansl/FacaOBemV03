package com.example.facaobemv03;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdTableDoador;
import com.google.android.material.snackbar.Snackbar;


public class InserirProdutosFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private EditText editTextNomeProduto;
    private EditText editTextQuantidade;
    private EditText editTextMarca;
    private EditText editTextDescricao;
    private Spinner spinnerDoadores;
    public static final int ID_CURSOR_LOADER_DOADORES = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inserir_produtos, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();

        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_inserir_produto);

        editTextNomeProduto = (EditText) view.findViewById(R.id.inputNomeProduto);
        editTextQuantidade = (EditText) view.findViewById(R.id.inputQuantidade);
        editTextMarca = (EditText) view.findViewById(R.id.inputMarcaProduto);
        editTextDescricao = (EditText) view.findViewById(R.id.inputDescricao);
        spinnerDoadores = (Spinner) view.findViewById(R.id.spinnerAlterarDoadores);

        mostraDadosSpinnerDoadores(null);

       LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_DOADORES, null, this);
    }



    public void cancelarCadastroProduto(){
        NavController navController = NavHostFragment.findNavController(InserirProdutosFragment.this);
        navController.navigate(R.id.action_inserirProdutosFragment_to_LIstaProdutosFragment);
    }

    public void cadastrarProduto(){
        String nomeProduto = editTextNomeProduto.getText().toString();
        long qtdProduto = Long.parseLong(editTextQuantidade.getText().toString());
        String marcaProduto = editTextMarca.getText().toString();
        String descricao = editTextDescricao.getText().toString();

        if(nomeProduto.length() <= 0){
            editTextNomeProduto.setError(getString(R.string.msgErrorNomeDoador));
            editTextNomeProduto.requestFocus();
            return;
        }
        else if(qtdProduto <= 0){
            editTextQuantidade.setError(getString(R.string.msgErrorNomeDoador));
            editTextQuantidade.requestFocus();
            return;
        }
        else if(marcaProduto.length() <= 0){
            editTextMarca.setError(getString(R.string.msgErrorNomeDoador));
            editTextMarca.requestFocus();
            return;
        }
        else if(descricao.length() <= 0){
            editTextDescricao.setError(getString(R.string.msgErrorNomeDoador));
            editTextDescricao.requestFocus();
            return;
        }

        long idDoador = spinnerDoadores.getSelectedItemId();

        ProdutoModelo produtoModelo = new ProdutoModelo();

        produtoModelo.setNomeProduto(nomeProduto);
        produtoModelo.setQuantidade(qtdProduto);
        produtoModelo.setMarcaProduto(marcaProduto);
        produtoModelo.setDescricao(descricao);
        produtoModelo.setIdDoador(idDoador);

        try {
            getActivity().getContentResolver().insert(FacaOBemrContentProvider.ENDERECO_PRODUTO, Converte.produtoToContentValues(produtoModelo));
            Toast.makeText(getContext(), R.string.msgSuccessProduct, Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(InserirProdutosFragment.this);
            navController.navigate(R.id.action_inserirProdutosFragment_to_LIstaProdutosFragment);
        } catch (Exception e) {
            Snackbar.make(editTextNomeProduto, R.string.msgErrorInserirProduto, Snackbar.LENGTH_SHORT).show();
        }
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        return new CursorLoader(
                getContext(),
                FacaOBemrContentProvider.ENDERECO_DOADOR,
                BdTableDoador.TODOS_CAMPOS,
                null,
                null,
                BdTableDoador.CAMPO_NOME_DOADOR
        );
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraDadosSpinnerDoadores(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraDadosSpinnerDoadores(null);
    }

    public void mostraDadosSpinnerDoadores(Cursor data){
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTableDoador.CAMPO_NOME_DOADOR},
                new int[]{android.R.id.text1}
        );

        spinnerDoadores.setAdapter(adapter);
    }
}
