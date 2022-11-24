package br.udesc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.server.ServerController;

public class ReadyToPlayTest {

    @Test
    public void verificaReadyToPlay() {
        User userValida = null;

        ServerController controller = ServerController.getInstance();
        Gson gson = new Gson();
        
        // Cria o usuario
        String jsonUser = controller.signUp("Gabriel", "123456", 1);
        User user = gson.fromJson(jsonUser, User.class);
        
        // Cria a partida
        String jsonMatch = controller.createMatch("Partida", 4);
        Match match = gson.fromJson(jsonMatch, Match.class);

        // Join na partida
        controller.joinMatch(user.getId(), match.getMatchId());

        try {
            String jsonValida = controller.readyToPlay(user.getId());
            userValida = gson.fromJson(jsonValida, User.class);
        } catch (Exception e) {}        

        assertNotNull(userValida);
    }

    @Test
    public void verificaUnreadyToPlay() {
        User userValida = null;

        ServerController controller = ServerController.getInstance();
        Gson gson = new Gson();
        
        // Cria o usuario
        String jsonUser = controller.signUp("Gabriel", "123456", 1);
        User user = gson.fromJson(jsonUser, User.class);
        
        // Cria a partida
        String jsonMatch = controller.createMatch("Partida", 4);
        Match match = gson.fromJson(jsonMatch, Match.class);

        // Join na partida
        controller.joinMatch(user.getId(), match.getMatchId());

        // Ready to play
        controller.readyToPlay(user.getId());

        try {
            String jsonValida = controller.unreadyToPlay(user.getId());
            userValida = gson.fromJson(jsonValida, User.class);
        } catch (Exception e) {}        

        assertNotNull(userValida);
    }

}
