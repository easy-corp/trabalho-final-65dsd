package com.example.uno.control.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.model.Jogador;
import com.example.uno.model.Jogo;

public class AdapterJogadores extends RecyclerView.Adapter<AdapterJogadores.ViewHolder> {

    Jogo jogo;

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

    public AdapterJogadores(Jogo jogo) {
        this.jogo = jogo;
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
        Jogador jogador = this.jogo.getJogadores().get(position);
        jogador.getAvatar().setClicado(false);

        holder.imgJogador.setBackgroundResource(jogador.getAvatar().getImg());
        holder.txtCartas.setText(jogador.getNumCartas() + " Cartas");
    }

    @Override
    public int getItemCount() {
        return this.jogo.getJogadores().size();
    }

}
