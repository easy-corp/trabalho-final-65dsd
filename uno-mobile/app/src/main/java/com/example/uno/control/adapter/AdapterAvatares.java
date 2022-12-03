package com.example.uno.control.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;
import com.example.uno.model.Avatar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AdapterAvatares extends RecyclerView.Adapter<AdapterAvatares.ViewHolder> {

    Map<Integer, Avatar> avatares;
    RecyclerView recyclerView;
    int posSelecionado;

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

    public AdapterAvatares(RecyclerView recyclerView, Map<Integer, Avatar> avatares) {
        this.recyclerView = recyclerView;
        this.avatares = avatares;
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
                for (Avatar av : avatares.values()) {
                    //Muda todos menos o clicado para não selecionado
                    if (av != avatar) {
                        av.click(false);
                    }
                }

                //Ajusta o clicado
                if (avatar.getImageUrl().indexOf("_selecionado") > 0) {
                    avatar.click(false);
                } else {
                    avatar.click(true);
                    posSelecionado = holder.getBindingAdapterPosition();
                }

                AdapterAvatares.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return avatares.size();
    }

    public int getPosSelecionado() {
        return this.posSelecionado;
    }

}
