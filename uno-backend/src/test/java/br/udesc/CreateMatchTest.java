package br.udesc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

import br.udesc.core.model.Match;
import br.udesc.core.server.ServerController;

public class CreateMatchTest {
    
    private static ServerController controller = ServerController.getInstance();

    //Verifica se a partida foi criada 
    @Test
    public void verificarPartidaCriada() {
        Match match = null;
        
        try {
            String json = controller.createMatch("Jogos da Galera", 4);
            Gson gson = new Gson();
            match = gson.fromJson(json, Match.class);
        } catch (Exception e) {
            e.getMessage();
        }

        assertNotNull(match);
    }

}
