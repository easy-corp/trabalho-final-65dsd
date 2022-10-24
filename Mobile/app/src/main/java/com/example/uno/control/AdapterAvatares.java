package com.example.uno.control;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAvatares extends RecyclerView.Adapter<AdapterAvatares.ViewHolder> {

    private List<Integer> avatares;

    //O tipo de view que vamos usar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.lay_frame);
            imgView = (ImageView) view.findViewById(R.id.imgAvatar);
        }
    }

    public AdapterAvatares() {
        this.avatares = new ArrayList<>();

        this.avatares.add(R.drawable.avatar_1);
        this.avatares.add(R.drawable.avatar_2);
        this.avatares.add(R.drawable.avatar_3);
        this.avatares.add(R.drawable.avatar_4);
        this.avatares.add(R.drawable.avatar_5);
        this.avatares.add(R.drawable.avatar_6);
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
        holder.imgView.setBackgroundResource(avatares.get(position));
    }

    @Override
    public int getItemCount() {
        return avatares.size();
    }

}
