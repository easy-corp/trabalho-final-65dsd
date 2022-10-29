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
import com.example.uno.model.Jogo;
import com.example.uno.model.Servidor;

public class AdapterJogos extends RecyclerView.Adapter<AdapterJogos.ViewHolder> {

    Servidor servidor;
    Context tela;

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

    public AdapterJogos(Context tela, Servidor servidor) {
        this.tela = tela;
        this.servidor = servidor;
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
        Jogo jogo = this.servidor.getJogos().get(position);

        holder.txtJogo.setText(jogo.getNome());
        holder.txtParticipantes.setText(jogo.getPartAtual() + "/" + jogo.getPartCapacidade());

        if (!jogo.isEntravel()) {
            holder.icEntrarJogo.setBackgroundResource(R.drawable.sala_indisponivel);
        }

        holder.icEntrarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jogo.isEntravel()) {
                    tela.startActivity(new Intent(tela, TelaJogo.class));
                } else {
                    Toast.makeText(tela, "A sala está cheia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.servidor.getJogos().size();
    }

}