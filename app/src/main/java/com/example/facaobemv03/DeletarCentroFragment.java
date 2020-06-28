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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facaobemv03.Models.CentroRecebimentoModelo;
import com.google.android.material.snackbar.Snackbar;


public class DeletarCentroFragment extends Fragment {
    private TextView textViewNomeCentro;
    private TextView textViewEndereco;
    private TextView textViewCidade;
    private TextView textViewCep;

    CentroRecebimentoModelo centroRecebimentoModelo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deletar_centro, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_deletar_centro);

        textViewNomeCentro = (TextView) view.findViewById(R.id.textViewNOmeInstituicao);
        textViewEndereco = (TextView) view.findViewById(R.id.textViewEndereco);
        textViewCidade = (TextView) view.findViewById(R.id.textViewCidade);
        textViewCep = (TextView) view.findViewById(R.id.textViewCep);

        Button btnDelete = (Button) view.findViewById(R.id.btnDeleteCentro);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCentro();
            }
        });

        Button btnCancelarDelete = (Button) view.findViewById(R.id.btnCancelaDeletarCentro);
        btnCancelarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarDeletarCentro();
            }
        });

        centroRecebimentoModelo = activity.getCentreoRecebimento();
        textViewNomeCentro.setText(centroRecebimentoModelo.getNomeInstituicao());
        textViewEndereco.setText(centroRecebimentoModelo.getEndereco());
        textViewCidade.setText(centroRecebimentoModelo.getCidade());
        textViewCep.setText(centroRecebimentoModelo.getCep());
    }

    public void cancelarDeletarCentro() {
        NavController navController = NavHostFragment.findNavController(DeletarCentroFragment.this);
        navController.navigate(R.id.action_deletarCentroFragment_to_listaCentroRecebimentoFragment);
    }

    public void deleteCentro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Eliminar Centro");
        builder.setMessage("Tem certeza que deseja eliminar o centro '" + centroRecebimentoModelo.getNomeInstituicao() + "'");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setPositiveButton("Sim, deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confimarDelecaoCentro();
            }
        });

        builder.setNegativeButton("Não, cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelarDeletarCentro();
            }
        });

        builder.show();
    }

    public void confimarDelecaoCentro() {
        try {
            Uri enderecoCentro = Uri.withAppendedPath(FacaOBemrContentProvider.ENDERECO_CENTRO, String.valueOf(centroRecebimentoModelo.getId()));

            int apagados = getActivity().getContentResolver().delete(enderecoCentro, null, null);

            if(apagados == 1){
                Toast.makeText(getContext(), R.string.msgSucessEliminarCentro, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(DeletarCentroFragment.this);
                navController.navigate(R.id.action_deletarCentroFragment_to_listaCentroRecebimentoFragment);
                return;
            }

        } catch (Exception e) {
            Snackbar.make(textViewNomeCentro, R.string.msgErrorEliminarCentro, Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}