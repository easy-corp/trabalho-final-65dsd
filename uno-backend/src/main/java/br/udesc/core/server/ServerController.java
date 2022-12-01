package br.udesc.core.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import br.udesc.core.model.Avatar;
import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.model.User.UserStatus;
import br.udesc.core.server.messages.CreateMatchMessage;
import br.udesc.core.server.messages.GetMatchesListMessage;
import br.udesc.core.server.messages.GetAvatarsMessage;
import br.udesc.core.server.messages.JoinMatchMessage;
import br.udesc.core.server.messages.LoginMessage;
import br.udesc.core.server.messages.MyProfileMessage;
import br.udesc.core.server.messages.QuitMatchMessage;
import br.udesc.core.server.messages.ReadyToPlayMessage;
import br.udesc.core.server.messages.SignUpMessage;

public class ServerController {

    private static ServerController instance;
    private Gson gson = new Gson();
    private Map<Integer, Avatar> avatars = buildAvatars();
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Match> matches = new HashMap<>();

    private ServerController() {
    }

    public synchronized static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }

        return instance;
    }

    //Recupera a lista de avatares
    public void getAvatars(GetAvatarsMessage message) {
        message.sendReply(getAvatarsList());
    }

    //Realiza o cadastro
    public void signUp(SignUpMessage message) {
        message.sendReply(signUp(message.getUsername(), message.getPassword(), message.getAvatarId()));
    }

    //Realiza login
    public void login(LoginMessage message) {
        User theUser = doLogin(message.getUsername(), message.getPassword());
        Server.getInstance().bindUser(theUser, message.getSocketClient());

        message.sendReply(gson.toJson(theUser));
    }

    //Recupera informações do usuário logado
    public void myProfile(MyProfileMessage message) {
        message.sendReply(myProfile(message.getUserId()));
    }

    //Cria uma nova partida
    public void createMatch(CreateMatchMessage message) {
        message.sendReply(createMatch(message.getName(), message.getQtdPlayers()));
    }

    //Recupera a lista de partidas
    public void getMatchesList(GetMatchesListMessage message) {
        message.sendReply(getMatchesList());
    }

    //Entra na partida
    public void joinMatch(JoinMatchMessage message) {
        message.sendReply(joinMatch(message.getUserId(), message.getMatchId()));
    }

    //Sai da partida
    public void quitMatch(QuitMatchMessage message) {
        message.sendReply(quitMatch(message.getUserId(), message.getMatchId()));
    }

    //Indica que está pronto para jogar
    public void readyToPlay(ReadyToPlayMessage message) {
        message.sendReply(readyToPlay(message.getUserId(), message.getMatchId()));
    }

    public String signUp(String username, String password, int avatarId) {
        User user = new User(username, password, avatars.get(avatarId));

        users.put(user.getId(), user);

        return gson.toJson(user);
    }

    public String login(String username, String password) {
        return gson.toJson(doLogin(username, password));
    }

    private User doLogin(String username, String password){
        User user = null;

        for (User us : users.values()) {         
            if (us.getName().contentEquals(username)
                    && us.getPassword().contentEquals(password)) {
                user = us;
            }
        }

        return user;
    }


    public String myProfile(int userId) {
        User user = users.get(userId);
        return gson.toJson(user);
    }

    public String createMatch(String name, int qtdPlayers) {
        Match match = new Match(name, qtdPlayers);

        this.matches.put(match.getMatchId(), match);

        return gson.toJson(match);
    }

    public String getAvatarsList() {
        return gson.toJson(avatars);
    }

    public String getMatchesList() {
        return gson.toJson(matches);
    }

    public String joinMatch(int userId, int matchId) {
        User user = users.get(userId);
        Match match = matches.get(matchId);

        match.addPlayer(user);

        return gson.toJson(match);
    }

    public String quitMatch(int userId, int matchId) {
        User user = users.get(userId);
        Match match = matches.get(matchId);

        match.removePlayer(user);

        return gson.toJson(match);
    }

    public String readyToPlay(int userId, int matchId) {
        Match match = matches.get(matchId);
        User user = match.getPlayers().get(userId);
        
        user.setStatus(UserStatus.READY);

        return gson.toJson(user);
    }

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
