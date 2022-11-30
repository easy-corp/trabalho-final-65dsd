package br.udesc;

import static org.junit.Assert.assertNotNull;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import br.udesc.core.model.Match;
import br.udesc.core.server.ServerController;

//Classe para verificar se a lista de partidas retorna 
//Essa lista vai ser usada para exibir as partidas para o usuario
public class MatchesListTest {

    private static ServerController controller = ServerController.getInstance();

    //Verifica se a lista de partida veio nula
    @Test
    public void verificarListaNula() {
        Map<Integer, Match> matches = null;

        try {
            String json = controller.getMatchesList();
            
            Gson gson = new Gson();
            Type listType = new TypeToken<Map<Integer, Match>>(){}.getType();
            matches = gson.fromJson(json, listType);
        } catch (Exception e) {
            e.getMessage();
        }
        
        assertNotNull(matches);
    }

    //Verifica se algum elemento da lista veio nulo
    @Test
    public void verificarElementoNulo() {
        //Cria um objeto nulo de partidas
        //Tenta buscar as partidas de um servidor
        Map<Integer, Match>matches = null;

        try {
            String json = controller.getMatchesList();
            
            Gson gson = new Gson();
            Type listType = new TypeToken<Map<Integer, Match>>(){}.getType();
            matches = gson.fromJson(json, listType);
        } catch (Exception e) {
            e.getMessage();
        }
        
        for (Match m : matches.values()) {
            assertNotNull(m);   
        }

    }

}