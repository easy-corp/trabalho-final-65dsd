package br.udesc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.gson.Gson;

import br.udesc.core.model.User;
import br.udesc.core.server.ServerController;

//Classe para verificar se o login funciona
//O login permite ao usuario acesso as funcoes do sistema
public class LoginTest {
    
    //Verifica se o objeto retornado existe
    @Test
    public void verificarLoginNulo() {
        //Tentar fazer login com informacoes pre definidas
        User user = null;

        ServerController controller = ServerController.getInstance();

        try {
            String json = controller.login("Gabriel", "123456");
            Gson gson = new Gson();
            user = gson.fromJson(json, User.class);
        } catch (Exception e) {
            e.getMessage();     
        }

        assertNotNull(user);
    }

}