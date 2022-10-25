package com.example.uno.control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.model.Jogador;

import java.util.ArrayList;
import java.util.List;

public class AdapterJogadores extends RecyclerView.Adapter<AdapterJogadores.ViewHolder> {

    List<Jogador> jogadores;

    //O tipo de view que vamos usar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imgJogador;
        TextView txtCartas;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layCartasJogador);
            imgJogador = (ImageView) view.findViewById(R.id.imgJogador);
            txtCartas = (TextView) view.findViewById(R.id.txtCartas);
        }
    }

    public AdapterJogadores() {
        this.jogadores = new ArrayList<>();

        this.jogadores.add(new Jogador("Luis", R.drawable.avatar_1, 6));
        this.jogadores.add(new Jogador("Gabriel", R.drawable.avatar_4, 3));
        this.jogadores.add(new Jogador("Murilo", R.drawable.avatar_5, 8));
        this.jogadores.add(new Jogador("Giovana", R.drawable.avatar_2, 13));
        this.jogadores.add(new Jogador("maria", R.drawable.avatar_6, 7));
    }

    @Override
    public AdapterJogadores.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycle_jogadores, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Pega o elemento na lista e joga o conteudo na view referente
    @Override
    public void onBindViewHolder(AdapterJogadores.ViewHolder holder, int position) {
        Jogador jogador = jogadores.get(position);

        holder.imgJogador.setBackgroundResource(jogador.getImg());
        holder.txtCartas.setText(jogador.getNumCartas() + " Cartas");
    }

    @Override
    public int getItemCount() {
        return jogadores.size();
    }

}
