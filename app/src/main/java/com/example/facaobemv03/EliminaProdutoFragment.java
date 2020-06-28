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

import com.example.facaobemv03.Models.ProdutoModelo;
import com.google.android.material.snackbar.Snackbar;


public class EliminaProdutoFragment extends Fragment {
    private TextView textViewNomeProduto;
    private TextView textViewQuantidade;
    private TextView textViewNomeDoadorItem;

    private ProdutoModelo produtoModelo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_produto, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_deletar_produto);

        textViewNomeProduto = (TextView) view.findViewById(R.id.textViewNomeProduto);
        textViewQuantidade = (TextView) view.findViewById(R.id.textViewQuantidade);
        textViewNomeDoadorItem = (TextView) view.findViewById(R.id.textViewNomeDoadorItem);


        Button btnDelete = (Button) view.findViewById(R.id.btnDeleteProduto);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduto();
            }
        });

        Button btnCancelarDelete = (Button) view.findViewById(R.id.btnCancelaDeletar);
        btnCancelarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarDeletarProduto();
            }
        });

        produtoModelo = activity.getProdutoModelo();
        textViewNomeProduto.setText(produtoModelo.getNomeProduto());
        textViewQuantidade.setText(String.valueOf(produtoModelo.getQuantidade()));
        textViewNomeDoadorItem.setText(produtoModelo.getDoador());
    }

    public void cancelarDeletarProduto() {
        NavController navController = NavHostFragment.findNavController(EliminaProdutoFragment.this);
        navController.navigate(R.id.action_eliminaProdutoFragment_to_LIstaProdutosFragment);
    }

    public void deleteProduto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Eliminar Produto");
        builder.setMessage("Tem certeza que deseja eliminar o produto '" + produtoModelo.getNomeProduto() + "'");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setPositiveButton("Sim, deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confimarDelecaoProduto();
            }
        });

        builder.setNegativeButton("Não, cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelarDeletarProduto();
            }
        });

        builder.show();
    }

    public void confimarDelecaoProduto() {
        try {
            Uri enderecoProdutos = Uri.withAppendedPath(FacaOBemrContentProvider.ENDERECO_PRODUTO, String.valueOf(produtoModelo.getId()));

            int apagados = getActivity().getContentResolver().delete(enderecoProdutos, null, null);

            if(apagados == 1){
                Toast.makeText(getContext(), R.string.msgSucessEliminarProduto, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(EliminaProdutoFragment.this);
                navController.navigate(R.id.action_eliminaProdutoFragment_to_LIstaProdutosFragment);
                return;
            }

        } catch (Exception e) {
            Snackbar.make(textViewNomeProduto, R.string.msgErrorEliminarProduto, Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}
