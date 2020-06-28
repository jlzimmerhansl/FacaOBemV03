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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.example.facaobemv03.database.BdTableCentrosRecebimento;
import com.example.facaobemv03.database.BdTableDoador;


public class ListaCentroRecebimentoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private AdaptadorCentro adaptadorCentro;
    private int id_CursorLoader_Centros= 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_centro_recebimento, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        Doador activity = (Doador) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_lista_centro);


        RecyclerView recyclerViewCentros = (RecyclerView) view.findViewById(R.id.recycleViewCentros);
        adaptadorCentro = new AdaptadorCentro(context);
        recyclerViewCentros.setAdapter(adaptadorCentro);
        recyclerViewCentros.setLayoutManager(new LinearLayoutManager(context));

        adaptadorCentro.setCursor(null);
        LoaderManager.getInstance(this).initLoader(id_CursorLoader_Centros, null,  this);
    }

    public void alteraCentro(){
        NavController navController = NavHostFragment.findNavController(ListaCentroRecebimentoFragment.this);
        navController.navigate(R.id.action_ListaDoadoresFragment_to_alteraDoadoresFragment);
    }

    public void novoDoador(){
        NavController navController = NavHostFragment.findNavController(ListaCentroRecebimentoFragment.this);
        navController.navigate(R.id.action_ListaDoadoresFragment_to_AdicionaDoadoresFragment);
    }

    public void deletarDoador(){
        NavController navController = NavHostFragment.findNavController(ListaCentroRecebimentoFragment.this);
        navController.navigate(R.id.action_ListaDoadoresFragment_to_eliminaDoadoresFragment);
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
        adaptadorCentro.setCursor(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorCentro.setCursor(null);
    }
}