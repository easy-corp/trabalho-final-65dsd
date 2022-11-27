package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.control.adapter.AdapterJogadoresResult;
import com.example.uno.model.Avatar;
import com.example.uno.model.Match;
import com.example.uno.model.User;

public class TelaResultados extends AppCompatActivity {

    private Match jogo = new Match("Jogos da Galera", 4);

    //RecyclerView
    private RecyclerView listaJogadores;
    private RecyclerView.Adapter adapterJogadores;
    private RecyclerView.LayoutManager layJogadores;

    //Elementos da tela
    private ImageView imgVencedorAvatar;
    private TextView txtVencedorNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultados);

        ImageView icVoltar = findViewById(R.id.icSair);
        ImageView icUsuario = findViewById(R.id.icUsuario);
        this.imgVencedorAvatar = findViewById(R.id.imgVencedorAvatar);
        this.txtVencedorNome = findViewById(R.id.txtVencedorNome);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class)));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class)));

        geraJogadores();
        criarRecyclerView();
    }

    public void criarRecyclerView() {
        listaJogadores = findViewById(R.id.listaJogadoresResultado);

        listaJogadores.setHasFixedSize(true);

        layJogadores = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaJogadores.setLayoutManager(layJogadores);

        adapterJogadores = new AdapterJogadoresResult(this.jogo, this);
        listaJogadores.setAdapter(adapterJogadores);
    }

    public void geraJogadores() {
        //Adiciona jogadores
        this.jogo.addPlayer(new User("Gabriel", "1234", new Avatar("avatar_4")));
        this.jogo.addPlayer(new User("Murilo", "1234", new Avatar("avatar_5")));
        this.jogo.addPlayer(new User("Giovana", "1234", new Avatar("avatar_2")));
        this.jogo.addPlayer(new User("Maria", "1234", new Avatar("avatar_6")));

        //Preenche vencedor
        User vencedor = new User("Luis", "1234", new Avatar("avatar_1"));
        vencedor.getAvatar().click(true);

        int image = getResources().getIdentifier(vencedor.getAvatar().getImageUrl(), "drawable", getPackageName());
        this.imgVencedorAvatar.setBackgroundResource(image);

        this.txtVencedorNome.setText(vencedor.getName());
    }
}