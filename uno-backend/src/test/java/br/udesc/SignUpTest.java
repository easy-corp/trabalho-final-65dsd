package br.udesc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.gson.Gson;

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
            user = gson.fromJson(json, User.class);
        } catch (Exception e) {
            e.getMessage();    
        }

        assertNotNull(user);
    }

}