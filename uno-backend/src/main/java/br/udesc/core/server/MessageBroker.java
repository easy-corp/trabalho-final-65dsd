package br.udesc.core.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.udesc.core.server.messages.AbstractMessage;

public class MessageBroker implements ClientSocketListener {

    private MessageBroker() {
    }

    private static MessageBroker instance;
    private ServerController controller = ServerController.getInstance();
    private Gson gson = new Gson();

    public static MessageBroker getInstance() {
        if (instance == null) {
            instance = new MessageBroker();
        }

        return instance;
    }

    @Override
    public void onMessage(String message, ClientSocketThread socketClient) {
        JsonObject messageObject = gson.fromJson(message, JsonObject.class);
        String type = messageObject.get("type").getAsString();
        if (type != null) {
            try {
                String caseFormatedName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, type);
                Class messageClass = Class.forName("br.udesc.core.server.messages." + caseFormatedName + "Message");
                try {
                    String methodName = Character.toLowerCase(caseFormatedName.charAt(0)) + caseFormatedName.substring(1);
                    AbstractMessage messageImpl = (AbstractMessage) messageClass.getDeclaredConstructor(ClientSocketThread.class).newInstance(socketClient);
                    messageImpl.setupProps(messageObject);
                    Method methodToInvoke = ServerController.class.getDeclaredMethod(methodName, messageImpl.getClass());         
                    methodToInvoke.invoke(controller, messageImpl);    
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                Logger.getGlobal().log(Level.SEVERE, "Erro ao instanciar a classe da mensagem:");
                e.printStackTrace();
            }
        } else {
            Logger.getGlobal().log(Level.SEVERE, "Erro ao processar mensagem, 'type' é inexistente!");
        }

    }

}
