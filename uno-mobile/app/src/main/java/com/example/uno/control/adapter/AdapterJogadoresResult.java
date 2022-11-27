package com.example.uno.control.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.TelaResultados;
import com.example.uno.model.Match;
import com.example.uno.model.User;

public class AdapterJogadoresResult extends RecyclerView.Adapter<AdapterJogadoresResult.ViewHolder> {

    private Match jogo;
    private TelaResultados telaResultados;

    //O tipo de view que vamos usar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imgJogador;
        TextView txtNome;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layJogadoresResult);
            imgJogador = (ImageView) view.findViewById(R.id.imgJogadorResult);
            txtNome = (TextView) view.findViewById(R.id.txtNomeResult);
        }
    }

    public AdapterJogadoresResult(Match jogo, TelaResultados telaResultados) {
        this.jogo = jogo;
        this.telaResultados = telaResultados;
    }

    //Cria e define o layout a ser utilizado
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycle_jogadores_result, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Pega o elemento na lista e joga o conteudo na view referente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User jogador = this.jogo.getPlayers().get(position);
        jogador.getAvatar().click(true);
        int image = this.telaResultados.getResources().getIdentifier(jogador.getAvatar().getImageUrl(), "drawable", telaResultados.getPackageName());

        holder.imgJogador.setBackgroundResource(image);
        holder.txtNome.setText(jogador.getName());
    }

    @Override
    public int getItemCount() {
        return this.jogo.getPlayers().size();
    }

}
