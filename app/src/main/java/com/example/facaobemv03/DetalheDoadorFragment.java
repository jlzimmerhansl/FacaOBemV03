package com.example.facaobemv03;

import android.content.Context;
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

import com.example.facaobemv03.Models.DoadorModelo;


public class DetalheDoadorFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_detalhe_doador, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_detalhe_doador);

        textViewNomeDoador = (TextView) view.findViewById(R.id.lblNomeDoador);
        textViewDataDoacao = (TextView) view.findViewById(R.id.lblDataDoacao);
        textViewEmailDoador = (TextView) view.findViewById(R.id.lblEmailDoador);
        textViewTelefoneDoador = (TextView) view.findViewById(R.id.lblTelefoneDoador);

        Button btnCadastraProduto = (Button) view.findViewById(R.id.btnCadastrarProduto);
        btnCadastraProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastraProduto();
            }
        });

        Button btnCancelarDetalhe = (Button) view.findViewById(R.id.btn_cancelarDeletarDoador);
        btnCancelarDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarDeletarDoador();
            }
        });

        Button btnVerListaProdutos = (Button) view.findViewById(R.id.btnVerListaProdutos);
        btnVerListaProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaProdutos();
            }
        });

        doadorModelo = activity.getDoadorModelo();
        textViewNomeDoador.setText(doadorModelo.getNomeDoador());
        textViewDataDoacao.setText(doadorModelo.getDataDoacao());
        textViewEmailDoador.setText(doadorModelo.getEmailDoador());
        textViewTelefoneDoador.setText(doadorModelo.getTelefoneDoador());
    }

    private void listaProdutos() {
        NavController navController = NavHostFragment.findNavController(DetalheDoadorFragment.this);
        navController.navigate(R.id.action_detalheDoadorFragment_to_LIstaProdutosFragment);
    }

    private void cadastraProduto() {
        NavController navController = NavHostFragment.findNavController(DetalheDoadorFragment.this);
        navController.navigate(R.id.action_detalheDoadorFragment_to_inserirProdutosFragment);
    }

    public void cancelarDeletarDoador() {
        NavController navController = NavHostFragment.findNavController(DetalheDoadorFragment.this);
        navController.navigate(R.id.action_detalheDoadorFragment_to_ListaDoadoresFragment);
    }
}