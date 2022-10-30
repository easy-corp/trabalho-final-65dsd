package com.example.uno.control.adapter;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.TelaJogo;
import com.example.uno.model.Carta;
import com.example.uno.model.Jogador;
import com.example.uno.model.Jogo;
import com.google.android.material.slider.Slider;

public class AdapterCartasJogador extends RecyclerView.Adapter<AdapterCartasJogador.ViewHolder> {

    TelaJogo telaJogo;
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

    public AdapterCartasJogador(TelaJogo telaJogo, Jogador jogador) {
        this.telaJogo = telaJogo;
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

        //Descartar carta da mão do jogador
        holder.imgCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se as carta pode ser dropada na mesa
                if (telaJogo.getJogo().isDropavel(carta)) {
                    jogador.getDeck().remove(carta);
                    telaJogo.atualizarCartaMesa(carta);
                    telaJogo.atualizarListas();
                } else {
//                    telaJogo.exibirMensagem("Não pode dropar essa carta.");

                    holder.imgCarta.startAnimation(AnimationUtils.loadAnimation(telaJogo, R.animator.shake));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jogador.getDeck().size();
    }

}
