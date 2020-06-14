package com.example.facaobemv03;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.facaobemv03.Models.DoadorModelo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorDoadores extends RecyclerView.Adapter<AdaptadorDoadores.ViewHolderDoadores> {

    private final Context context;

    private Cursor cursor= null;

    public AdaptadorDoadores(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        if(cursor != this.cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolderDoadores} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolderDoadores, int position)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolderDoadores, int)
     */
    @NonNull
    @Override
    public ViewHolderDoadores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemDOador = LayoutInflater.from(context).inflate(R.layout.item_doador, parent, false);
        return new ViewHolderDoadores(itemDOador);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolderDoadores#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolderDoadores#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolderDoadores, int List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDoadores holder, int position) {
        cursor.moveToPosition(position);
        DoadorModelo doadorModelo = Converte.cursorToDoador(cursor);
        holder.setDoador(doadorModelo);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(cursor == null) return 0;

        return cursor.getCount();
    }

    public class ViewHolderDoadores extends RecyclerView.ViewHolder {
        private final TextView TextViewNomeDoador;
        private final TextView TextViewDataDoacao;
        private DoadorModelo doadorModelo = null;

        public ViewHolderDoadores(@NonNull View itemView) {
            super(itemView);

            TextViewNomeDoador = (TextView) itemView.findViewById(R.id.textViewNomeDoador);
            TextViewDataDoacao = (TextView) itemView.findViewById(R.id.textViewDataDoacao);
        }

        public void setDoador(DoadorModelo doadorModelo){
            this.doadorModelo = doadorModelo;

            TextViewNomeDoador.setText(doadorModelo.getNomeDoador());
            TextViewDataDoacao.setText(doadorModelo.getDataDoacao());
        }
    }
}