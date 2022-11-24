package br.udesc.core.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.udesc.core.model.Avatar;
import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.model.User.UserStatus;

public class ServerController {

    private static ServerController instance;
    private Gson gson = new Gson();
    private List<Avatar> avatars = buildAvatars();
    private List<User> users = new ArrayList<>();
    private List<Match> matches = new ArrayList<>();

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
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        
        //Busca o avatar selecionado
        for (Avatar av : avatars) {
            if (av.getId() == avatarId) {
                user.setAvatar(av);
            }
        }

        users.add(user);

        return gson.toJson(user);
    }

    //Verifica nome e senha do usuário
    public String login(String username, String password){
        User user = null;

        for (User us : users) {
            if (us.getName().contentEquals(username) && us.getPassword().contentEquals(password)) {
                user = us;
            }
        }
        
        return gson.toJson(user);
    }

    //Localiza o usuário dentre os demais
    public String myProfile(int userId) {
        for (User us : users) {
            if (us.getId() == userId) {
                return gson.toJson(us);
            }
        }
        
        return null;
    }

    //Entra no servidor
    public String joinServer(String ip, int port, int userId) {
        return "";
    }

    //Cria uma nova partida
    public String createMatch(String name, int qtdPlayers) {
        Match match = new Match(name, qtdPlayers);
        
        this.matches.add(match);

        return gson.toJson(match);
    }

    //Lista as partidas
    public String getMatches(String ip, int port) {
        return gson.toJson(matches);
    }

    //Entra na partida
    public String joinMatch(int userId, int matchId) {
        for (User u : users) {
            if (u.getId() == userId) {
                for (Match m : matches) {
                    if (m.getMatchId() == matchId) {
                        m.addPlayer(u);
                        return gson.toJson(m);
                    }
                }
                break;
            }
        }

        return null;
    }

    //Sai da partida
    public String quitMatch(int userId, int matchId){
        for (User u : users) {
            if (u.getId() == userId) {
                for (Match m : matches) {
                    if (m.getMatchId() == matchId) {
                        m.removePlayer(u);
                        return gson.toJson(m);
                    }
                }
                break;
            }
        }

        return null;
    }

    //Define que o usuário está pronto para jogar
    public String readyToPlay(int userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                u.setStatus(UserStatus.READY);

                return gson.toJson(u);
            }
        }

        return null;
    }

    //Define que o usuário não está pronto para jogar
    public String unreadyToPlay(int userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                u.setStatus(UserStatus.UNREADY);

                return gson.toJson(u);
            }
        }

        return null;
    }

    //Constrói avatares para serem usados ao longo da aplicação
    private List<Avatar> buildAvatars() {
        List<Avatar> avatars = new ArrayList<>();

        avatars.add(new Avatar(1, "avatar_1"));
        avatars.add(new Avatar(2, "avatar_2"));
        avatars.add(new Avatar(3, "avatar_3"));
        avatars.add(new Avatar(4, "avatar_4"));
        avatars.add(new Avatar(5, "avatar_5"));
        avatars.add(new Avatar(6, "avatar_6"));

        return avatars;
    }

}
