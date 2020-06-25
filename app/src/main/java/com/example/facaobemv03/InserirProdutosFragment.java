package com.example.facaobemv03;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.facaobemv03.Models.DoadorModelo;


public class InserirProdutosFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView llblNomeDoador;
    private EditText editTextNomeProduto;
    private EditText editTextQuantidade;

    private DoadorModelo doadorModelo;
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
        doadorModelo = activity.getDoadorModelo();

        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_inserir_produto);

        llblNomeDoador = (TextView) view.findViewById(R.id.lblNomeDoador);
        editTextNomeProduto = (EditText) view.findViewById(R.id.inputNomeProduto);
        editTextQuantidade = (EditText) view.findViewById(R.id.inputQuantidade);

        llblNomeDoador.setText(doadorModelo.getNomeDoador());
    }

    public void cancelarCadastroProduto(){

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
