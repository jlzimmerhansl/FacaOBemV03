package com.example.facaobemv03;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.facaobemv03.Models.DoadorModelo;
import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdTableProduto;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorProdutos extends RecyclerView.Adapter<AdaptadorProdutos.ViewHolderProdutos> {

    private final Context context;
    private Cursor cursor = null;



    public void setCursor(Cursor cursor){
        if(cursor != this.cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }
    public AdaptadorProdutos(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderProdutos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemProdutos = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        return new ViewHolderProdutos(itemProdutos);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProdutos holder, int position) {
        cursor.moveToPosition(position);
        ProdutoModelo produtoModelo = Converte.cursorToProduto(cursor);

        holder.setProduto(produtoModelo);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) return 0;

        return cursor.getCount();
    }

    public ProdutoModelo getProdutoSelecionado(){
        if(viewHolderProdutosSelecionado == null) return null;

        return viewHolderProdutosSelecionado.produtoModelo;
    }

    private ViewHolderProdutos viewHolderProdutosSelecionado = null;
    public class ViewHolderProdutos extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewNomeProduto;
        private final TextView textViewQuantidade;
        private final TextView textViewNomeDoador;

        private ProdutoModelo produtoModelo = null;


        public ViewHolderProdutos(@NonNull View itemView) {
            super(itemView);

            textViewNomeProduto = (TextView) itemView.findViewById(R.id.textViewNomeProduto);
            textViewQuantidade = (TextView) itemView.findViewById(R.id.textViewQuantidade);
            textViewNomeDoador = (TextView) itemView.findViewById(R.id.textViewNomeDoadorItem);
            itemView.setOnClickListener(this);

        }

        public void setProduto(ProdutoModelo produtoModelo){
            this.produtoModelo = produtoModelo;

            textViewNomeProduto.setText(produtoModelo.getNomeProduto());
            textViewQuantidade.setText(String.valueOf(produtoModelo.getQuantidade()));
            textViewNomeDoador.setText(String.valueOf(produtoModelo.getDoador()));
        }

        @Override
        public void onClick(View view) {
            if(viewHolderProdutosSelecionado == this){
                return;
            }

            if(viewHolderProdutosSelecionado != null){
                viewHolderProdutosSelecionado.tirarSeleca();
            }

            viewHolderProdutosSelecionado = this;
            seleciona();

            Doador activity = (Doador) AdaptadorProdutos.this.context;
            activity.atualizaOpcoesDeMenuListaProdutos();
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.Gray);
        }

        private void tirarSeleca() {
            itemView.setBackgroundResource(R.color.primaryTextColor);
        }
    }
}
