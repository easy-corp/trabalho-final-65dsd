package com.example.uno.control.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.TelaJogo;
import com.example.uno.model.Avatar;
import com.example.uno.model.Match;

import java.util.ArrayList;
import java.util.List;

public class AdapterJogos extends RecyclerView.Adapter<AdapterJogos.ViewHolder> {

    List<Match> matches;
    Context tela;
    int jogadorId;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView txtJogo;
        TextView txtParticipantes;
        ImageView icParticipantes;
        ImageView icEntrarJogo;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layListaJogos);
            txtJogo = (TextView) view.findViewById(R.id.txtJogo);
            txtParticipantes = (TextView) view.findViewById(R.id.txtParticipantes);
            icParticipantes = (ImageView) view.findViewById(R.id.icParticipantes);
            icEntrarJogo = (ImageView) view.findViewById(R.id.icEntrarJogo);
        }
    }

    public AdapterJogos(Context tela, List<Match> matches, int jogadorId) {
        this.tela = tela;
        this.matches = matches;
        this.jogadorId = jogadorId;

        //Se não tiver nenhum jogo
        if (this.matches == null) {
            this.matches = new ArrayList<>();
        }

    }

    @Override
    public AdapterJogos.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycle_jogos, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Pega o elemento na lista e joga o conteudo na view referente
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match jogo = matches.get(position);

        holder.txtJogo.setText(jogo.getName());
        holder.txtParticipantes.setText(jogo.getPlayers().size() + "/" + jogo.getQtdPlayers());

        if (!jogo.isEntravel()) {
            holder.icEntrarJogo.setBackgroundResource(R.drawable.sala_indisponivel);
        }

        holder.icEntrarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jogo.isEntravel()) {
                    //Coloca o id da partida associado a intent
                    Intent intent = new Intent(tela, TelaJogo.class);
                    intent.putExtra("matchId", String.valueOf(jogo.getMatchId()));
                    intent.putExtra("userId", String.valueOf(jogadorId));
                    tela.startActivity(intent);
                } else {
                    Toast.makeText(tela, "A sala está cheia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.matches.size();
    }

}