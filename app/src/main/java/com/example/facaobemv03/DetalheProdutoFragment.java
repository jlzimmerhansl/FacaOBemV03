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

import com.example.facaobemv03.Models.ProdutoDetalheModelo;
import com.example.facaobemv03.Models.ProdutoModelo;


public class DetalheProdutoFragment extends Fragment {

    private TextView textViewNomeProduto;
    private TextView textViewQUantidade;
    private TextView textViewMarca;
    private TextView textViewDescricao;

    private ProdutoModelo produtoModelo;
    private ProdutoDetalheModelo produtoDetalheModelo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalhe_produto, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_detalhe_produto);

        textViewNomeProduto = (TextView) view.findViewById(R.id.textViewNomeProdutoDetalhe);
        textViewQUantidade = (TextView) view.findViewById(R.id.textViewQuantidadeProdutoDetalhe);
        textViewMarca = (TextView) view.findViewById(R.id.textViewMarceDetalhe);
        textViewDescricao = (TextView) view.findViewById(R.id.textViewMarceDetalhe);



        produtoModelo = activity.getProdutoModelo();
        produtoDetalheModelo = activity.getProdutoMOdeloDetalhe();
        textViewNomeProduto.setText(produtoModelo.getNomeProduto());
        textViewQUantidade.setText(String.valueOf(produtoModelo.getQuantidade()));

        if(textViewMarca != null){
            textViewMarca.setText(produtoDetalheModelo.getMarcaProduto());

        }

        if(textViewDescricao != null){
            textViewDescricao.setText(produtoDetalheModelo.getDescricao());
        }
    }

    public void cadastraDetalhe() {
        NavController navController = NavHostFragment.findNavController(DetalheProdutoFragment.this);
        navController.navigate(R.id.action_detalheProdutoFragment_to_cadastrarDetalheFragment);
    }

    public void cancelarDetalheProduto() {
        NavController navController = NavHostFragment.findNavController(DetalheProdutoFragment.this);
        navController.navigate(R.id.action_detalheProdutoFragment_to_LIstaProdutosFragment);
    }
}