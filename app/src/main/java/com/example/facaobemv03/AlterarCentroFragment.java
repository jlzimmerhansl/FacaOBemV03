package com.example.facaobemv03;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.facaobemv03.Models.CentroRecebimentoModelo;
import com.example.facaobemv03.database.BdTableCentrosRecebimento;
import com.example.facaobemv03.database.BdTableDoador;
import com.google.android.material.snackbar.Snackbar;


public class AlterarCentroFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText nomeCentro;
    private EditText endereco;
    private EditText cidade;
    private EditText cep;

    private CentroRecebimentoModelo centroRecebimentoModelo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alterar_centro, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();

        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_alterar_centro);

        nomeCentro = (EditText) view.findViewById(R.id.inputNemoCentro);
        endereco = (EditText) view.findViewById(R.id.inputEndereco);
        cidade = (EditText) view.findViewById(R.id.inputCidade);
        cep = (EditText) view.findViewById(R.id.inputCep);

        centroRecebimentoModelo = activity.getCentreoRecebimento();

        nomeCentro.setText(centroRecebimentoModelo.getNomeInstituicao());
        endereco.setText(centroRecebimentoModelo.getEndereco());
        cidade.setText(centroRecebimentoModelo.getCidade());
        cep.setText(centroRecebimentoModelo.getCep());


    }

    public void cancelaAlterarCentro(){
        NavController navController = NavHostFragment.findNavController(AlterarCentroFragment.this);
        navController.navigate(R.id.action_alterarCentroFragment_to_listaCentroRecebimentoFragment);
    }

    public void alterarCentro(){
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

        Doador activity = (Doador) getActivity();
        CentroRecebimentoModelo centroRecebimentoModelo = activity.getCentreoRecebimento();

        centroRecebimentoModelo.setNomeInstituicao(centro);
        centroRecebimentoModelo.setEndereco(Endereco);
        centroRecebimentoModelo.setCidade(Cidade);
        centroRecebimentoModelo.setCep(Cep);


        try {
            Uri enderecoCentro = Uri.withAppendedPath(FacaOBemrContentProvider.ENDERECO_CENTRO, String.valueOf(centroRecebimentoModelo.getId()));

            int registros = getActivity().getContentResolver().update(enderecoCentro, Converte.centroRecebimentoToContentValues(centroRecebimentoModelo), BdTableCentrosRecebimento._ID + "=?", new String[]{String.valueOf(centroRecebimentoModelo.getId())});

            if (registros == 1) {
                Toast.makeText(getContext(), R.string.msgUpdateCentroSucess, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(AlterarCentroFragment.this);
                navController.navigate(R.id.action_alterarCentroFragment_to_listaCentroRecebimentoFragment);
                return;
            }
        } catch (Exception e) {
            Snackbar.make(nomeCentro, R.string.msgErrorAlterarCentro, Snackbar.LENGTH_SHORT).show();
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new androidx.loader.content.CursorLoader(
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