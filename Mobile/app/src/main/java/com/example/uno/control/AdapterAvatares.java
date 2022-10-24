package com.example.uno.control;

import android.annotation.SuppressLint;
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

    private List<Integer[]> avatares;
    RecyclerView recyclerView;
    int posUltimoClick;

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

    public AdapterAvatares(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.posUltimoClick = -1;

        this.avatares = new ArrayList<>();

        this.avatares.add(new Integer[] {R.drawable.avatar_1, R.drawable.avatar_1_selecionado});
        this.avatares.add(new Integer[] {R.drawable.avatar_2, R.drawable.avatar_2_selecionado});
        this.avatares.add(new Integer[] {R.drawable.avatar_3, R.drawable.avatar_3_selecionado});
        this.avatares.add(new Integer[] {R.drawable.avatar_4, R.drawable.avatar_4_selecionado});
        this.avatares.add(new Integer[] {R.drawable.avatar_5, R.drawable.avatar_5_selecionado});
        this.avatares.add(new Integer[] {R.drawable.avatar_6, R.drawable.avatar_6_selecionado});
    }

    @Override
    public AdapterAvatares.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycle_avatares, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Pega o elemento na lista e joga o conteudo na view referente
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imgView.setBackgroundResource(avatares.get(position)[0]);

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Caso tenha sido feito um click anteriormente
                if (posUltimoClick != -1) {
                    //Reverte a alteracao no ultimo clicado
                    View UltimoClick = recyclerView.getLayoutManager().findViewByPosition(posUltimoClick);
                    UltimoClick.setBackgroundResource(avatares.get(posUltimoClick)[0]);
                    notifyItemChanged(posUltimoClick);
                }

                //Atualiza posicao e ajusta o item clicado
                posUltimoClick = position;
                holder.imgView.setBackgroundResource(avatares.get(position)[1]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return avatares.size();
    }

}
