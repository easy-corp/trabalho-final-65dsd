package com.example.uno.control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.model.Carta;

import java.util.ArrayList;
import java.util.List;

public class AdapterCartasJogador extends RecyclerView.Adapter<AdapterCartasJogador.ViewHolder> {

    List<Carta> deck;

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

    public AdapterCartasJogador() {
        this.deck = new ArrayList<>();

        this.deck.add(new Carta("1", "RED", R.drawable.red_1));
        this.deck.add(new Carta("4", "BLUE", R.drawable.blue_4));
        this.deck.add(new Carta("4", "BLUE", R.drawable.blue_4));
        this.deck.add(new Carta("8", "GREEN", R.drawable.green_8));
        this.deck.add(new Carta("1", "RED", R.drawable.red_1));
        this.deck.add(new Carta("8", "GREEN", R.drawable.green_8));
        this.deck.add(new Carta("8", "GREEN", R.drawable.green_8));
        this.deck.add(new Carta("4", "BLUE", R.drawable.blue_4));
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
        Carta carta = deck.get(position);

        holder.imgCarta.setBackgroundResource(carta.getImg());
    }

    @Override
    public int getItemCount() {
        return deck.size();
    }

}
