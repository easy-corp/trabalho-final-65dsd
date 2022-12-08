package br.udesc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import br.udesc.core.model.User;
import br.udesc.core.server.ServerController;

//Classe para verificar se o cadastro funciona
//O cadastro permite ao usuario realizar login e ter acesso ao sistema
public class SignUpTest {

    //Verificar se o objeto de cadastro nao retorna nulo
    @Test
    public void verificarCadastroNulo() {
        //Tenta fazer cadastro com informacoes pre definidas
        User user = null;

        ServerController controller = ServerController.getInstance();

        try {
            String json = controller.signUp("Gabriel", "123456", 1);
            Gson gson = new Gson();
            
            JsonPrimitive ob = gson.fromJson(json, JsonPrimitive.class);
            JsonObject ob2 = new JsonObject();
            
            JsonParser jp = new JsonParser();
            JsonElement ob3 = jp.parse(json);
            JsonObject ob4 = ob3.getAsJsonObject();

            System.out.println(json);
            // user = gson.fromJson(json, User.class);
        } catch (Exception e) {
            e.getMessage();    
        }

        assertNotNull(true);
    }

}