package br.udesc.core.server;

public class ServerController {

    private static ServerController instance;

    private ServerController() {}

    public synchronized static ServerController getInstance(){
        if(instance == null){
            instance = new ServerController();
        }

        return instance;
    }

    public String getAvatars(){
        return "";
    }

    public String signUp(String username, String password, int avatarId){
        return "";
    }

    public String login(String username, String password){
        return "";
    }

    public String myProfile(int userId){
        return "";
    }

    public String joinServer(String ip, int port, int userId){
        return "";
    }

    public String getMatches(String ip, int port){
        return "";
    }

    public String joinMatch(int userId, int matchId){
        return "";
    }

    public String quitMatch(int userId, int matchId){
        return "";
    }

    public String readyToPlay(int userId, int matchId){
        return "";
    }



}
