package com.example.facaobemv03;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdTableDoador;
import com.example.facaobemv03.database.BdTableProduto;


public class LIstaProdutosFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private AdaptadorProdutos adaptadorProdutos;
    private int id_CursorLoader_Produtos = 0;

    public ProdutoModelo getProdutoSelecionado(){
        return adaptadorProdutos.getProdutoSelecionado();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_l_ista_produtos, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_lista_produtos);


        RecyclerView recyclerViewProdutos = (RecyclerView) view.findViewById(R.id.recycleViewProdutos);
        adaptadorProdutos = new AdaptadorProdutos(context);
        recyclerViewProdutos.setAdapter(adaptadorProdutos);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(context));

        adaptadorProdutos.setCursor(null);
        LoaderManager.getInstance(this).initLoader(id_CursorLoader_Produtos, null,  this);


    }

    public void CadastrarProduto(){
        NavController navController = NavHostFragment.findNavController(LIstaProdutosFragment.this);
        navController.navigate(R.id.action_LIstaProdutosFragment_to_inserirProdutosFragment);

    }

    public void AlterarProduto(){
        NavController navController = NavHostFragment.findNavController(LIstaProdutosFragment.this);
        navController.navigate(R.id.action_LIstaProdutosFragment_to_alterarProdutoFragment);

    }

    public void deletarProduto(){
        NavController navController = NavHostFragment.findNavController(LIstaProdutosFragment.this);
        navController.navigate(R.id.action_LIstaProdutosFragment_to_eliminaProdutoFragment);

    }

    public void cancelarListaProdutos(){
        NavController navController = NavHostFragment.findNavController(LIstaProdutosFragment.this);
        navController.navigate(R.id.action_LIstaProdutosFragment_to_ListaDoadoresFragment);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
       // String select = "(" + BdTableProduto.DOADOR_ID + "=" + doadorModelo.getId()  +  ")";
        return new CursorLoader(
                getContext(),
                FacaOBemrContentProvider.ENDERECO_PRODUTO,
                BdTableProduto.TODOS_CAMPOS,
                null,
                null,
                BdTableProduto.NOME_PRODUTO
        );
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorProdutos.setCursor(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorProdutos.setCursor(null);
    }
}
