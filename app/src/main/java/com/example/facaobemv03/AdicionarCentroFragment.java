package com.example.facaobemv03;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facaobemv03.Models.CentroRecebimentoModelo;
import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.database.BdTableCentrosRecebimento;
import com.example.facaobemv03.database.BdTableDoador;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class AdicionarCentroFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText nomeCentro;
    private EditText endereco;
    private EditText cidade;
    private EditText cep;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adicionar_centro, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();

        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_adicionar_centro);

        nomeCentro = (EditText) view.findViewById(R.id.inputNemoCentro);
        endereco = (EditText) view.findViewById(R.id.inputEndereco);
        cidade = (EditText) view.findViewById(R.id.inputCidade);
        cep = (EditText) view.findViewById(R.id.inputCep);


    }

    public void cancelaCadastroCentro(){
        NavController navController = NavHostFragment.findNavController(AdicionarCentroFragment.this);
        navController.navigate(R.id.action_adicionarCentroFragment_to_listaCentroRecebimentoFragment);
    }

    public void cadastrarCentro(){
        String centro = nomeCentro.getText().toString();
        String Endereco = endereco.getText().toString();
        String Cidade = cidade.getText().toString();
        String Cep = cep.getText().toString();

        if(centro.length() <= 0){
            nomeCentro.setError(getString(R.string.msgErrorNomeInstituicao));
            nomeCentro.requestFocus();
            return;
        }
        else if(Endereco.length() <= 0){
            endereco.setError(getString(R.string.msgErrorEndereco));
            endereco.requestFocus();
            return;
        }
        else if(Cidade.length() <= 0){
            cidade.setError(getString(R.string.msgErrorCidade));
            cidade.requestFocus();
            return;
        }
        else if(Cep.length() <= 0 ){
            cep.setError(getString(R.string.msgErrorCep));
            cep.requestFocus();
            return;
        }

        CentroRecebimentoModelo centroRecebimentoModelo = new CentroRecebimentoModelo();
        centroRecebimentoModelo.setNomeInstituicao(centro);
        centroRecebimentoModelo.setEndereco(Endereco);
        centroRecebimentoModelo.setCidade(Cidade);
        centroRecebimentoModelo.setCep(Cep);

        try {
            getActivity().getContentResolver().insert(FacaOBemrContentProvider.ENDERECO_CENTRO, Converte.centroRecebimentoToContentValues(centroRecebimentoModelo));
            Toast.makeText(getContext(), R.string.msgCentroInserido, Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(AdicionarCentroFragment.this);
            navController.navigate(R.id.action_adicionarCentroFragment_to_listaCentroRecebimentoFragment);
        } catch (Exception e) {
            Snackbar.make(nomeCentro, R.string.msgErrorInserirCentro, Snackbar.LENGTH_SHORT).show();
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                getContext(),
                FacaOBemrContentProvider.ENDERECO_CENTRO,
                BdTableCentrosRecebimento.TODOS_CAMPOS,
                null,
                null,
                BdTableCentrosRecebimento.CAMPO_NOME_CENTRO
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}