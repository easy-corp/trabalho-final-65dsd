package com.example.uno.control.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.TelaJogo;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.model.Card;
import com.example.uno.model.User;

public class AdapterCartasJogador extends RecyclerView.Adapter<AdapterCartasJogador.ViewHolder> {

    TelaJogo telaJogo;
    User jogador;

    //O tipo de view que vamos usar
    public static class ViewHolder extends  RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imgCarta;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layJogadoresResult);
            imgCarta = (ImageView) view.findViewById(R.id.imgCarta);
        }
    }

    public AdapterCartasJogador(User jogador, TelaJogo telaJogo) {
        this.jogador = jogador;
        this.telaJogo = telaJogo;
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
        Card carta = jogador.getDeck().get(position);
        int image = telaJogo.getResources().getIdentifier(carta.getImageUrl(), "drawable", telaJogo.getPackageName());

        holder.imgCarta.setBackgroundResource(image);

        //Descartar carta da mão do jogador
        holder.imgCarta.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if (telaJogo.getMyTurn()) {
                    //Se a carta pode ser dropada na mesa
                    if (telaJogo.getJogo().isDropavel(carta)) {
                        //Atualiza a quantidade de cartas do jogador da vez
                        jogador.removeCarta(carta);
                        telaJogo.getJogo().getPlayers().get(jogador.getUserId()).descartaCarta();

                        telaJogo.atualizarCartaMesa(carta);
                        telaJogo.atualizarListas();

                        telaJogo.setMyTurn(false);

                        String msg = new MessageBuilder()
                            .withType("play-card")
                            .withParam("userId", String.valueOf(jogador.getUserId()))
                            .withParam("matchId", String.valueOf(telaJogo.getJogo().getMatchId()))
                            .withParam("cardSymbol", carta.getSimbolo())
                            .withParam("cardColor", String.valueOf(carta.getColor()))
                            .withParam("cardImageUrl", carta.getImageUrl())
                            .build();

                        telaJogo.getBinder().getService().enviarMensagem(msg);
                    } else {
                        holder.imgCarta.startAnimation(AnimationUtils.loadAnimation(telaJogo, R.animator.shake));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jogador.getDeck().size();
    }


}
