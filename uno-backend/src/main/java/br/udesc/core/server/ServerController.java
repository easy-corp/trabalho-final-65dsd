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
import br.udesc.core.server.messages.CreateMatchMessage;
import br.udesc.core.server.messages.GetMatchesMessage;
import br.udesc.core.server.messages.JoinMatchMessage;
import br.udesc.core.server.messages.LoginMessage;
import br.udesc.core.server.messages.MyProfileMessage;
import br.udesc.core.server.messages.QuitMatchMessage;
import br.udesc.core.server.messages.ReadyToPlayMessage;
import br.udesc.core.server.messages.SignupMessage;

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

    public String getAvatars() {
        return gson.toJson(avatars);
    }

    public void signUp(SignupMessage message) {
        message.sendReply(signUp(message.getUsername(), message.getPassword(), message.getAvatarId()));
    }

    public void login(LoginMessage message) {
        message.sendReply(login(message.getUsername(), message.getPassword()));
    }

    public void createMatch(CreateMatchMessage message) {
        message.sendReply(createMatch(message.getName(), message.getQtdPlayers()));
    }

    public void myProfile(MyProfileMessage message) {
        message.sendReply(myProfile(message.getUserId()));
    }

    // Entra na partida
    public void joinMatch(JoinMatchMessage message) {
        message.sendReply(joinMatch(message.getUserId(), message.getMatchId()));
    }

    public void readyToPlay(ReadyToPlayMessage message) {
        message.sendReply(readyToPlay(message.getUserId()));
    }

    public void getMatchesList(GetMatchesMessage message) {
        message.sendReply(getMatchesList());
    }

    public String signUp(String username, String password, int avatarId) {
        User user = new User(username, password, avatars.get(avatarId));

        users.put(user.getId(), user);

        return gson.toJson(user);
    }

    public String login(String username, String password) {
        User user = null;

        for (User us : users.values()) {
            if (us.getName().contentEquals(username)
                    && us.getPassword().contentEquals(password)) {
                user = us;
            }
        }

        return gson.toJson(user);
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

    public String getMatchesList() {
        return gson.toJson(matches);
    }

    public String joinMatch(int userId, int matchId) {
        User user = users.get(userId);
        Match match = matches.get(matchId);

        match.addPlayer(user);

        return gson.toJson(match);
    }

    public void quitMatch(QuitMatchMessage message) {
        User user = users.get(message.getUserId());
        Match match = matches.get(message.getMatchId());

        match.removePlayer(user);

        message.sendReply(gson.toJson(match));
    }

    public String readyToPlay(int userId) {
        User user = users.get(userId);
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
