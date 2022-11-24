package com.example.uno.model;

import java.util.ArrayList;
import java.util.List;

public class GameServer {

    private String ip;                  //Ip do servidor
    private int port;                   //Porta do servidor
    private List<Match> matches;        //Lista de partidas no servidor

    public GameServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.matches = new ArrayList<>();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public List<Match> getMatches() {
        return matches;
    }

}
