package com.example.uno.control;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.model.Avatar;

import java.util.ArrayList;
import java.util.List;

public class AdapterAvatares extends RecyclerView.Adapter<AdapterAvatares.ViewHolder> {

    List<Avatar> avatares;
    RecyclerView recyclerView;
    int posUltimoClick;

    //O tipo de view que vamos usar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imgView;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.lay_frame);
            imgView = (ImageView) view.findViewById(R.id.imgAvatar);
        }
    }

    public AdapterAvatares(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.posUltimoClick = -1;

        this.avatares = new ArrayList<>();

        this.avatares.add(new Avatar(R.drawable.avatar_1, R.drawable.avatar_1_selecionado));
        this.avatares.add(new Avatar(R.drawable.avatar_2, R.drawable.avatar_2_selecionado));
        this.avatares.add(new Avatar(R.drawable.avatar_3, R.drawable.avatar_3_selecionado));
        this.avatares.add(new Avatar(R.drawable.avatar_4, R.drawable.avatar_4_selecionado));
        this.avatares.add(new Avatar(R.drawable.avatar_5, R.drawable.avatar_5_selecionado));
        this.avatares.add(new Avatar(R.drawable.avatar_6, R.drawable.avatar_6_selecionado));
    }

    @Override
    public AdapterAvatares.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycle_avatares, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Pega o elemento na lista e joga o conteudo na view referente
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Avatar avatar = avatares.get(position);
        holder.imgView.setBackgroundResource(avatar.getImgNaoSelecionado());

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Caso tenha sido feito um click anteriormente
                if (avatar.isClicado()) {
                    holder.imgView.setBackgroundResource(avatar.getImgNaoSelecionado());
                    avatar.setClicado(false);
                } else {
                    holder.imgView.setBackgroundResource(avatar.getImgSelecionado());
                    avatar.setClicado(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return avatares.size();
    }

}
