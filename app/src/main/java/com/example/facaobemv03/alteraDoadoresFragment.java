package com.example.facaobemv03;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.database.BdTableDoador;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class alteraDoadoresFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText editTextNomeDoador;
    private TextView editTextDataSelecionada;
    private EditText editTextEmailDoador;
    private EditText editTextTelefoneDoador;
    private Button btnDataSelecionada;
    private int ano, mes, dia;
    private int id_CursorLoader_Doadores = 0;

    private DoadorModelo doadorModelo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alteradoadores, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();

        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_alterar_doador);

        editTextNomeDoador = (EditText) view.findViewById(R.id.inputNomeDoadorAltera);
        editTextDataSelecionada = (TextView) view.findViewById(R.id.txtDataSelecionadaAlterar);
        btnDataSelecionada = (Button) view.findViewById(R.id.btnSeleciaonaDataAltera);
        editTextEmailDoador = (EditText) view.findViewById(R.id.inputEmailDoadorAltera);
        editTextTelefoneDoador = (EditText) view.findViewById(R.id.inputTelefoneDoadorAltera);

        doadorModelo = activity.getDoadorModelo();
        editTextNomeDoador.setText(doadorModelo.getNomeDoador());
        editTextDataSelecionada.setText(doadorModelo.getDataDoacao());
        editTextEmailDoador.setText(doadorModelo.getEmailDoador());
        editTextTelefoneDoador.setText(doadorModelo.getTelefoneDoador());

        view.findViewById(R.id.btnSeleciaonaDataAltera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == btnDataSelecionada){
                    final Calendar calendario = Calendar.getInstance();
                    ano = calendario.get(Calendar.YEAR);
                    mes = calendario.get(Calendar.MONTH);
                    dia = calendario.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            editTextDataSelecionada.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }, ano, mes, dia);
                    datePickerDialog.show();
                }
            }
        });

    }

    public void cancelar(){
        NavController navController = NavHostFragment.findNavController(alteraDoadoresFragment.this);
        navController.navigate(R.id.action_alteraDoadoresFragment_to_ListaDoadoresFragment);
    }

    public void cadastrarAlteraDoador(){
        String nomeDoDoador = editTextNomeDoador.getText().toString();
        String dataDoacao = editTextDataSelecionada.getText().toString();
        String email = editTextEmailDoador.getText().toString();
        String telefone = editTextTelefoneDoador.getText().toString();

        if(nomeDoDoador.length() <= 0){
            editTextNomeDoador.setError(getString(R.string.msgErrorNomeDoador));
            editTextNomeDoador.requestFocus();
            return;
        }
        else if(dataDoacao.length() <= 0){
            editTextDataSelecionada.setError(getString(R.string.msgErrorData));
            editTextDataSelecionada.requestFocus();
            return;
        }
        else if(email.length() <= 0){
            editTextEmailDoador.setError(getString(R.string.msgErrorEmailDoador));
            editTextEmailDoador.requestFocus();
            return;
        }
        else if(telefone.length() <= 0 ){
            editTextTelefoneDoador.setError(getString(R.string.msgErrorTelefone));
            editTextTelefoneDoador.requestFocus();
            return;
        }

        Doador activity = (Doador) getActivity();
        DoadorModelo doadorModelo = activity.getDoadorModelo();

        doadorModelo.setNomeDoador(nomeDoDoador);
        doadorModelo.setDataDoacao(dataDoacao);
        doadorModelo.setEmailDoador(email);
        doadorModelo.setTelefoneDoador(telefone);

        try {
            Uri enderecoDoador = Uri.withAppendedPath(FacaOBemrContentProvider.ENDERECO_DOADOR, String.valueOf(doadorModelo.getId()));

            int registros = getActivity().getContentResolver().update(enderecoDoador, Converte.doadorToContentValue(doadorModelo), BdTableDoador._ID + "=?", new String[]{String.valueOf(doadorModelo.getId())});

            if (registros == 1) {
                Toast.makeText(getContext(), R.string.msgUpdateDoadorSucess, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(alteraDoadoresFragment.this);
                navController.navigate(R.id.action_alteraDoadoresFragment_to_ListaDoadoresFragment);
                return;
            }

        } catch (Exception e) {
            Snackbar.make(editTextNomeDoador, R.string.msgErrorUpdateDoador, Snackbar.LENGTH_SHORT).show();
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

    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
