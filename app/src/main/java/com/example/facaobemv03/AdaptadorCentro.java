package com.example.facaobemv03;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.facaobemv03.Models.CentroRecebimentoModelo;
import com.example.facaobemv03.Models.DoadorModelo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorCentro  extends RecyclerView.Adapter<AdaptadorCentro.ViewHolderCentro> {

    private final Context context;

    private Cursor cursor= null;

    public AdaptadorCentro(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        if(cursor != this.cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolderCentro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemCentro = LayoutInflater.from(context).inflate(R.layout.item_centro, parent, false);
        return new AdaptadorCentro.ViewHolderCentro(itemCentro);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderCentro holder, int position) {
        cursor.moveToPosition(position);
        CentroRecebimentoModelo centroRecebimentoModelo = Converte.cursorToPCentroRecebimento(cursor);
        holder.setCentro(centroRecebimentoModelo);
    }


    @Override
    public int getItemCount() {
        if(cursor == null) return 0;

        return cursor.getCount();
    }

    private AdaptadorCentro.ViewHolderCentro viewHolderCentroSelecionado = null;
    public class ViewHolderCentro extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewNomeInstituicao;
        private final TextView TextViewEndereco;
        private final TextView TextViewCidade;
        private final TextView TextViewCep;
        private CentroRecebimentoModelo centroRecebimentoModelo = null;

        public ViewHolderCentro(@NonNull View itemView) {
            super(itemView);

            textViewNomeInstituicao = (TextView) itemView.findViewById(R.id.textViewNomeInstituicao);
            TextViewEndereco = (TextView) itemView.findViewById(R.id.textViewEndereco);
            TextViewCidade = (TextView) itemView.findViewById(R.id.textViewCidade);
            TextViewCep = (TextView) itemView.findViewById(R.id.textViewCep);

            itemView.setOnClickListener(this);
        }

        public void setCentro(CentroRecebimentoModelo centroRecebimentoModelo){
            this.centroRecebimentoModelo = centroRecebimentoModelo;

            textViewNomeInstituicao.setText(centroRecebimentoModelo.getNomeInstituicao());
            TextViewEndereco.setText(centroRecebimentoModelo.getEndereco());
            TextViewCidade.setText(centroRecebimentoModelo.getCidade());
            TextViewCep.setText(centroRecebimentoModelo.getCep());

        }

        @Override
        public void onClick(View view) {
            if(viewHolderCentroSelecionado == this){
                return;
            }

            if(viewHolderCentroSelecionado != null){
                viewHolderCentroSelecionado.tirarSelecao();
            }

            viewHolderCentroSelecionado = this;
            selecionaItem();

            Doador activity = (Doador) AdaptadorCentro.this.context;
            activity.centroAlterado(centroRecebimentoModelo);
        }

        private void selecionaItem() {
            itemView.setBackgroundResource(R.color.Gray);
        }

        private void tirarSelecao() {
            itemView.setBackgroundResource(R.color.primaryTextColor);
        }
    }
}
