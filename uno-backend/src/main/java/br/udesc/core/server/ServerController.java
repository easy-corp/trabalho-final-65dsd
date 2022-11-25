package br.udesc.core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import br.udesc.core.model.Avatar;
import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.model.User.UserStatus;

public class ServerController {

    private static ServerController instance;
    private Gson gson = new Gson();
    private Map<Integer, Avatar> avatars = buildAvatars();
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Match> matches = new HashMap<>();

    private ServerController() {}

    public synchronized static ServerController getInstance(){
        if(instance == null){
            instance = new ServerController();
        }

        return instance;
    }

    public String getAvatars(){
        return gson.toJson(avatars);
    }

    //Realiza cadastro do usuário
    public String signUp(String username, String password, int avatarId) {
        User user = new User(username, password, avatars.get(avatarId));
        
        users.put(user.getId(), user);

        return gson.toJson(user);
    }

    //Verifica nome e senha do usuário
    public String login(String username, String password){
        User user = null;

        for (User us : users.values()) {
            if (us.getName().contentEquals(username) && us.getPassword().contentEquals(password)) {
                user = us;
            }
        }
        
        return gson.toJson(user);
    }

    //Localiza o usuário dentre os demais
    public String myProfile(int userId) {
        User user = users.get(userId);
        
        return gson.toJson(user);
    }

    //Entra no servidor
    public String joinServer(String ip, int port, int userId) {
        return "";
    }

    //Cria uma nova partida
    public String createMatch(String name, int qtdPlayers) {
        Match match = new Match(name, qtdPlayers);
        
        this.matches.put(match.getMatchId(), match);

        return gson.toJson(match);
    }

    //Lista as partidas
    public String getMatches(String ip, int port) {
        return gson.toJson(matches);
    }

    //Entra na partida
    public String joinMatch(int userId, int matchId) {
        User user = users.get(userId);
        Match match = matches.get(matchId);

        match.addPlayer(user);

        return gson.toJson(match);
    }

    //Sai da partida
    public String quitMatch(int userId, int matchId){
        User user = users.get(userId);
        Match match = matches.get(matchId);

        match.removePlayer(user);

        return gson.toJson(match);
    }

    //Define que o usuário está pronto para jogar
    public String readyToPlay(int userId) {
        User user = users.get(userId);
        
        user.setStatus(UserStatus.READY);

        return gson.toJson(user);
    }

    //Define que o usuário não está pronto para jogar
    public String unreadyToPlay(int userId) {
        User user = users.get(userId);

        user.setStatus(UserStatus.UNREADY);

        return gson.toJson(user);
    }

    //Constrói avatares para serem usados ao longo da aplicação
    private Map<Integer, Avatar> buildAvatars() {
        Map<Integer, Avatar> avatars = new HashMap<>();

        avatars.put(1, new Avatar("avatar_1"));
        avatars.put(2, new Avatar("avatar_2"));
        avatars.put(3, new Avatar("avatar_3"));
        avatars.put(4, new Avatar("avatar_4"));
        avatars.put(5, new Avatar("avatar_5"));
        avatars.put(6, new Avatar("avatar_6"));

        return avatars;
    }

    public void setAvatars(Map<Integer, Avatar> avatars) {
        this.avatars = avatars;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(Map<Integer, User> users) {
        this.users = users;
    }

    public Map<Integer, Match> getMatches() {
        return matches;
    }

    public void setMatches(Map<Integer, Match> matches) {
        this.matches = matches;
    }    

}
