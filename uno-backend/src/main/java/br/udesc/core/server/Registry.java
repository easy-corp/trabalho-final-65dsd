package br.udesc.core.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import br.udesc.core.model.Avatar;
import br.udesc.core.model.Match;
import br.udesc.core.model.User;

public class Registry {

    // Os métodos aqui definidos irão fazer o acquire do recurso, em seguida
    // modificar o mesmo e só então liberar ele para que outro processo possa mexer

    private static Registry instance; // A instância desse Singleton
    private Semaphore mutex; // Semaphore para controle ao acesso dos dados
    private Map<Integer, Avatar> avatars = buildAvatars(); // Lista de avatares
    private Map<Integer, User> users = new HashMap<>(); // Lista de usuários cadastrados
    private Map<Integer, Match> matches = new HashMap<>(); // Lista de partidas
    private Map<Integer, MatchRunner> runners = new HashMap<>();

    private Registry() {
        mutex = new Semaphore(1);
    }

    public synchronized static Registry getInstance() {
        if (instance == null) {
            instance = new Registry();
        }

        return instance;
    }

    public User getUser(int id) {
        try {
            mutex.acquire();
            User targetUser = users.get(id);
            mutex.release();
            return targetUser;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Collection<User> getUsersList() {
        try {
            mutex.acquire();
            Collection<User> us = users.values();
            mutex.release();
            return us;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void putUser(User user) {
        try {
            mutex.acquire();
            this.users.put(user.getId(), user);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Avatar getAvatar(int id) {
        try {
            mutex.acquire();
            Avatar targetAvatar = avatars.get(id);
            mutex.release();
            return targetAvatar;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Avatar> getAvatarsList() {
        try {
            mutex.acquire();
            Collection<Avatar> av = avatars.values();
            mutex.release();
            return av;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putMatch(Match match) {
        try {
            mutex.acquire();
            matches.put(match.getMatchId(), match);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateMatch(Match matchOther) {
        try {
            mutex.acquire();
            Match match = matches.get(matchOther.getMatchId());

            match.getPlayers().putAll(matchOther.getPlayers());
            match.getMatchdeck().addAll(matchOther.getMatchdeck());
            match.getDiscard().addAll(matchOther.getDiscard());

            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Match getMatch(int matchId) {
        try {
            mutex.acquire();
            Match match = matches.get(matchId);
            mutex.release();
            return match;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Match> getMatchesList() {
        try {
            mutex.acquire();
            Collection<Match> mts = matches.values();
            mutex.release();
            return mts;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void removePlayerFromMatch(int matchId, int userId) {
        try {
            mutex.acquire();
            this.matches.get(matchId).removePlayer(this.users.get(userId));
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addRunner(int matchId, MatchRunner runner) {
        try {
            mutex.acquire();
            this.runners.put(matchId, runner);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MatchRunner getRunner(int matchId) {
        try {
            mutex.acquire();
            MatchRunner runner = runners.get(matchId);
            mutex.release();
            return runner;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<Integer, Avatar> buildAvatars() {
        Map<Integer, Avatar> avatars = new HashMap<>();

        avatars.put(0, new Avatar("avatar_1"));
        avatars.put(1, new Avatar("avatar_2"));
        avatars.put(2, new Avatar("avatar_3"));
        avatars.put(3, new Avatar("avatar_4"));
        avatars.put(4, new Avatar("avatar_5"));
        avatars.put(5, new Avatar("avatar_6"));

        return avatars;
    }

    public List<Match> getMatchesWithPlayer(int userId) {
        List<Match> partidas = new ArrayList<>();

        try {
            mutex.acquire();
            
            //Verifica as partidas em que esse jogador está
            for (Match m : this.matches.values()) {
                if (m.getPlayers().containsKey(userId)) {
                    partidas.add(m);
                }
            }

            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return partidas;
    }

    public List<Match> getWinsPlayer(int userId) {
        List<Match> vitorias = new ArrayList<>();

        try {
            mutex.acquire();
            
            //Verifica as partidas em que esse jogador está
            for (Match m : this.matches.values()) {
                //Se a partida já tiver sido jogada
                if (m.getPlayers().size() > 0) {
                    //Verifica quem ganhou a partida
                    User winner = (User) m.getPlayers().values().toArray()[0];

                    for (User player : m.getPlayers().values()) {
                        if (player.getDeck().size() < winner.getDeck().size()) {
                            winner = player;
                        }
                    }
    
                    //Se o vencedor for o jogador coloca essa junto das partidas que ele ganhou
                    if (winner.getId() == userId) {
                        vitorias.add(m);
                    }
                }
            }

            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return vitorias;
    }

}
