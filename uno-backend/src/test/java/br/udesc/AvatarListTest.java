package br.udesc;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.udesc.core.model.Avatar;
import br.udesc.core.server.ServerController;

//Classe para verificar se a lista de avatares retorna 
//Essa lista vai ser usada para fazer o cadastro do usuario
public class AvatarListTest {

    private static ServerController controller = ServerController.getInstance();

    //Verifica se a lista veio nula
    @Test
    public void verificarListaNula() {
        //Valor retornado pelo server
        Gson gson = new Gson();
        String json = controller.getAvatarsList();
        List<Avatar> listaConvertida = null;

        try {
            //Transforma o Gson novamente em uma lista do tipo Avatar
            Type listType = new TypeToken<List<Avatar>>(){}.getType();
            listaConvertida = gson.fromJson(json, listType);
        } catch (Exception e) {
            e.getMessage();
        }

        assertNotNull(listaConvertida);
    }

    //Verifica se algum elmento da lista veio nulo
    @Test
    public void verificarElementoNulo() {
        //Valor retornado pelo server
        Gson gson = new Gson();
        String json = controller.getAvatarsList();
        List<Avatar> listaConvertida = null;

        try {
            //Transforma o Gson novamente em uma lista do tipo Avatar
            Type listType = new TypeToken<List<Avatar>>(){}.getType();
            listaConvertida = gson.fromJson(json, listType);
        } catch (Exception e) {
            e.getMessage();
        }

        for (Avatar av : listaConvertida) {
            assertNotNull(av);
        }        
    }
}
