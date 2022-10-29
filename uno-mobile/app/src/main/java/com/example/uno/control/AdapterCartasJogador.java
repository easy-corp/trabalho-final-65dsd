package com.example.uno.control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.model.Carta;
import com.example.uno.model.Jogador;

import java.util.ArrayList;
import java.util.List;

public class AdapterCartasJogador extends RecyclerView.Adapter<AdapterCartasJogador.ViewHolder> {

    Jogador jogador;

    //O tipo de view que vamos usar
    public static class ViewHolder extends  RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imgCarta;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layCartasJogador);
            imgCarta = (ImageView) view.findViewById(R.id.imgCarta);
        }
    }

    public AdapterCartasJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    @Override
    public AdapterCartasJogador.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycle_cartas_jogador, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Pega o elemento na lista e joga o conteudo na view referente
    @Override
    public void onBindViewHolder(AdapterCartasJogador.ViewHolder holder, int position) {
        Carta carta = jogador.getDeck().get(position);

        holder.imgCarta.setBackgroundResource(carta.getImg());
    }

    @Override
    public int getItemCount() {
        return jogador.getDeck().size();
    }

}
