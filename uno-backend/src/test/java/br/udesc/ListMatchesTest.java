package br.udesc;

import static org.junit.Assert.assertNotNull;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import br.udesc.core.model.Match;
import br.udesc.core.server.ServerController;

//Classe para verificar se a lista de partidas retorna 
//Essa lista vai ser usada para exibir as partidas para o usuario
public class ListMatchesTest {

    private static ServerController controller = ServerController.getInstance();

    //Verifica se a lista de partida veio nula
    @Test
    public void verificarListaNula() {
        List<Match> matches = null;

        try {
            String json = controller.getMatches("127.0.0.1", 8080);
            
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Match>>(){}.getType();
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
        List<Match> matches = null;

        try {
            String json = controller.getMatches("127.0.0.1", 8080);
            
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Match>>(){}.getType();
            matches = gson.fromJson(json, listType);
        } catch (Exception e) {
            e.getMessage();
        }
        
        for (Match m : matches) {
            assertNotNull(m);   
        }

    }

}