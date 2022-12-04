package com.example.uno.control.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.TelaJogo;
import com.example.uno.model.User;
import com.example.uno.model.Match;

public class AdapterJogadores extends RecyclerView.Adapter<AdapterJogadores.ViewHolder> {

    private Match jogo;
    private TelaJogo telaJogo;

    //O tipo de view que vamos usar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imgJogador;
        TextView txtCartas;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layJogadoresResult);
            imgJogador = (ImageView) view.findViewById(R.id.imgJogadorResult);
            txtCartas = (TextView) view.findViewById(R.id.txtNomeResult);
        }
    }

    public AdapterJogadores(Match jogo, TelaJogo telaJogo) {
        this.jogo = jogo;
        this.telaJogo = telaJogo;
    }

    //Cria e define o layout a ser utilizado
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
        User jogador = this.jogo.getPlayers().get(position);
        int image = telaJogo.getResources().getIdentifier(jogador.getAvatar().getImageUrl(), "drawable", telaJogo.getPackageName());

        holder.imgJogador.setBackgroundResource(image);
        holder.txtCartas.setText(jogador.getNumCartas() + " Cartas");
    }

    @Override
    public int getItemCount() {
        return this.jogo.getPlayers().size();
    }

}
