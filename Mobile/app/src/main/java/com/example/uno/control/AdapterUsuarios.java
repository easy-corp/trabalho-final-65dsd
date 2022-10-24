package com.example.uno.control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolder> {

    private List<String> dados;

    //O tipo de view que vamos usar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private FrameLayout layout;

        public ViewHolder(View view) {
            super(view);

            layout = (FrameLayout) view.findViewById(R.id.lay);
            textView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public AdapterUsuarios(List<String> dataset) {
        this.dados = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    //Pega o elemento na lista e joga o conteudo na view referente
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.getTextView().setText(dados.get(position));
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

}
