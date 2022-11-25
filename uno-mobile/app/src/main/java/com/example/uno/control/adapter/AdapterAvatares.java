package com.example.uno.control.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.model.Avatar;

import java.util.ArrayList;
import java.util.List;

public class AdapterAvatares extends RecyclerView.Adapter<AdapterAvatares.ViewHolder> {

    List<Avatar> avatares;
    RecyclerView recyclerView;

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

        this.avatares = new ArrayList<>();

        this.avatares.add(new Avatar("avatar_1"));
        this.avatares.add(new Avatar("avatar_2"));
        this.avatares.add(new Avatar("avatar_3"));
        this.avatares.add(new Avatar("avatar_4"));
        this.avatares.add(new Avatar("avatar_5"));
        this.avatares.add(new Avatar("avatar_6"));
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
        int image = recyclerView.getContext().getResources().getIdentifier(avatar.getImageUrl(), "drawable", recyclerView.getContext().getPackageName());
        holder.imgView.setBackgroundResource(image);

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Caso tenha sido feito um click anteriormente
                for (Avatar av : avatares) {
                    //Muda todos menos o clicado
                    if (av != avatar) {
                        av.setClicado(false);
                    }
                }

                //Ajusta o clicado
                if (avatar.isClicado()) {
                    avatar.setClicado(false);
                } else {
                    avatar.setClicado(true);
                }

                AdapterAvatares.this.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return avatares.size();
    }

}
