package com.example.facaobemv03;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.service.autofill.TextValueSanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facaobemv03.Models.DoadorModelo;
import com.google.android.material.snackbar.Snackbar;


public class EliminaDoadoresFragment extends Fragment {
    private TextView textViewNomeDoador;
    private TextView textViewDataDoacao;
    private TextView textViewEmailDoador;
    private TextView textViewTelefoneDoador;

    private DoadorModelo doadorModelo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_doadores, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_deletardoador);

        textViewNomeDoador = (TextView) view.findViewById(R.id.lblNomeDoador);
        textViewDataDoacao = (TextView) view.findViewById(R.id.lblDataDoacao);
        textViewEmailDoador = (TextView) view.findViewById(R.id.lblEmailDoador);
        textViewTelefoneDoador = (TextView) view.findViewById(R.id.lblTelefoneDoador);

        Button btnDelete = (Button) view.findViewById(R.id.btnConfirmarEliminar);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDoador();
            }
        });

        Button btnCancelarDelete = (Button) view.findViewById(R.id.btn_cancelarDeletarDoador);
        btnCancelarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarDeletarDoador();
            }
        });

        doadorModelo = activity.getDoadorModelo();
        textViewNomeDoador.setText(doadorModelo.getNomeDoador());
        textViewDataDoacao.setText(doadorModelo.getDataDoacao());
        textViewEmailDoador.setText(doadorModelo.getEmailDoador());
        textViewTelefoneDoador.setText(doadorModelo.getTelefoneDoador());
    }

    public void deleteDoador() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Eliminar Livro");
        builder.setMessage("Tem certeza que deseja eliminar o doador '" + doadorModelo.getNomeDoador() + "'");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setPositiveButton("Sim, deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confimarDelecaoDoador();
            }
        });

        builder.setNegativeButton("Não, cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelarDeletarDoador();
            }
        });

        builder.show();
    }

    private void confimarDelecaoDoador() {
        try {
            Uri enderecoDoadores = Uri.withAppendedPath(FacaOBemrContentProvider.ENDERECO_DOADOR, String.valueOf(doadorModelo.getId()));

            int apagados = getActivity().getContentResolver().delete(enderecoDoadores, null, null);

            if(apagados == 1){
                Toast.makeText(getContext(), R.string.msgSucessEliminarDoador, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(EliminaDoadoresFragment.this);
                navController.navigate(R.id.action_eliminaDoadoresFragment_to_ListaDoadoresFragment);
                return;
            }

        } catch (Exception e) {
            Snackbar.make(textViewNomeDoador, R.string.msgErrorEliminarDoador, Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    public void cancelarDeletarDoador() {
        NavController navController = NavHostFragment.findNavController(EliminaDoadoresFragment.this);
        navController.navigate(R.id.action_eliminaDoadoresFragment_to_ListaDoadoresFragment);
    }

}
