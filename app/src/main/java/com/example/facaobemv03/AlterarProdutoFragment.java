package com.example.facaobemv03;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdTableDoador;
import com.example.facaobemv03.database.BdTableProduto;
import com.google.android.material.snackbar.Snackbar;


public class AlterarProdutoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER_DOADORES = 0;

    private EditText editTextNomeProduto;
    private EditText editTextQuantidade;
    private EditText editTextMarca;
    private EditText editTextDescricao;
    private Spinner spinnerDoadores;

    private ProdutoModelo produtoModelo;
    private boolean doadoresCarregados = false;
    private boolean doadoresAtualizados = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alterar_produto, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        Doador activity = (Doador) getActivity();

        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_alterar_produto);

        editTextNomeProduto = (EditText) view.findViewById(R.id.inputNomeProduto);
        editTextQuantidade = (EditText) view.findViewById(R.id.inputQuantidade);
        spinnerDoadores = (Spinner) view.findViewById(R.id.spinnerAlterarDoadores);

        mostraDadosSpinnerDoadores(null);

        produtoModelo = activity.getProdutoModelo();
        editTextNomeProduto.setText(produtoModelo.getNomeProduto());
        editTextQuantidade.setText(String.valueOf(produtoModelo.getQuantidade()));

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_DOADORES, null, this);

        atualizaDoadorSelecionado();
    }

    private void atualizaDoadorSelecionado() {
        if(!doadoresCarregados) return;;
        if(doadoresAtualizados) return;

        long idDoador = produtoModelo.getIdDoador();

        for(int i = 0; i < spinnerDoadores.getCount(); i++){
            if(spinnerDoadores.getItemIdAtPosition(i) == idDoador){
                spinnerDoadores.setSelection(i, true);
                break;
            }
        }

        doadoresAtualizados = true;
    }

    public void cancelarAlterarProduto(){
        NavController navController = NavHostFragment.findNavController(AlterarProdutoFragment.this);
        navController.navigate(R.id.action_alterarProdutoFragment_to_LIstaProdutosFragment);
    }

    public void alterarPrduto(){
        String nomeProduto = editTextNomeProduto.getText().toString();
        long qtdProduto = Long.parseLong(editTextQuantidade.getText().toString());

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

        long idDoador = spinnerDoadores.getSelectedItemId();

        Doador activity = (Doador) getActivity();

        ProdutoModelo produtoModelo = activity.getProdutoModelo();

        produtoModelo.setNomeProduto(nomeProduto);
        produtoModelo.setQuantidade(qtdProduto);
        produtoModelo.setIdDoador(idDoador);

        try {
            Uri enderecoProduto = Uri.withAppendedPath(FacaOBemrContentProvider.ENDERECO_PRODUTO, String.valueOf(produtoModelo.getId()));

            int registros = getActivity().getContentResolver().update(enderecoProduto, Converte.produtoToContentValues(produtoModelo), null, null);

            if(registros == 1){
                Toast.makeText(getContext(), R.string.msgSuccessAlterProduct, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(AlterarProdutoFragment.this);
                navController.navigate(R.id.action_alterarProdutoFragment_to_LIstaProdutosFragment);
            }
        } catch (Exception e) {
            Snackbar.make(editTextNomeProduto, R.string.msgErrorAlterarProduto, Snackbar.LENGTH_SHORT).show();
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
        doadoresCarregados = true;
        atualizaDoadorSelecionado();
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
