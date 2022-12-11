package br.udesc.core.server;

import java.util.Collection;
import java.awt.EventQueue;

import com.google.gson.Gson;

import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.model.User.UserStatus;
import br.udesc.core.server.messages.*;

public class ServerController {

    private static ServerController instance; // Instância do Singleton
    private Registry registry = Registry.getInstance(); // Realiza o controle para integridad dos dados
    private Gson gson = new Gson();

    private ServerController() {
        User us = new User("Luis", "1234", this.registry.getAvatar(0));
        this.registry.putUser(us);

        User us2 = new User("Murilo", "123", this.registry.getAvatar(3));
        this.registry.putUser(us2);

        Match match = new Match("Partida de Teste", 4);
        this.registry.putMatch(match);
    }

    public synchronized static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }

        return instance;
    }

    // Testa a conexão com o servidor
    public void testConnection(TestConnectionMessage message) {
        message.sendReply("Connection ok");
    }

    // Recupera a lista de avatares
    public void getAvatarsList(GetAvatarsListMessage message) {
        message.sendReply(getAvatarsList());
    }

    // Realiza o cadastro
    public void signUp(SignUpMessage message) {
        message.sendReply(signUp(message.getUsername(), message.getPassword(), message.getAvatarId()));
    }

    // Realiza login
    public void login(LoginMessage message) {
        User theUser = doLogin(message.getUsername(), message.getPassword());

        // Se o login não exister, ele não vai tentar bindar o usuário
        if (theUser != null) {
            Server.getInstance().bindUser(theUser, message.getSocketClient());
        }

        message.sendReply(gson.toJson(theUser));
    }

    // Recupera informações do usuário logado
    public void myProfile(MyProfileMessage message) {
        message.sendReply(myProfile(message.getUserId()));
    }

    // Recupera informações de uma partida específica
    public void myProfile(GetMatchMessage message) {
        message.sendReply(getMatch(message.getMatchId()));
    }

    // Cria uma nova partida
    public void createMatch(CreateMatchMessage message) {
        message.sendReply(createMatch(message.getName(), message.getQtdPlayers()));
    }

    // Recupera uma partida
    public void getMatch(GetMatchMessage message) {
        message.sendReply(getMatch(message.getMatchId()));
    }

    // Recupera a lista de partidas
    public void getMatchesList(GetMatchesListMessage message) {
        message.sendReply(getMatchesList());
    }

    // Recupera as partidas as quais esse player participou
    public void getMatchesWithPlayer(GetMatchesWithPlayerMessage message) {
        message.sendReply(matchesWithPlayer(message.getUserId()));
    }

    // Recupera as partidas as quais esse player ganhou
    public void getWinsPlayer(GetWinsPlayerMessage message) {
        message.sendReply(matchesWinner(message.getUserId()));
    }

    // Entra na partida
    public void joinMatch(JoinMatchMessage message) {
        message.sendReply(joinMatch(message.getUserId(), message.getMatchId()));
        Match m = this.registry.getMatch(message.getMatchId());
        User u = this.registry.getUser(message.getUserId());

        TypedMessage messageToPlayers = new TypedMessage("player-joined", u);

        // Avisar os otro que o cara entro
        for (User user : m.getPlayers().values()) {
            if (user.getId() != message.getUserId()) {
                MessageBroker.getInstance().sendMessageToUser(user.getId(), gson.toJson(messageToPlayers));
            }
        }
    }

    // Sai da partida
    public void quitMatch(QuitMatchMessage message) {
        quitMatch(message.getUserId(), message.getMatchId());
        Match m = this.registry.getMatch(message.getMatchId());
        User u = this.registry.getUser(message.getUserId());
        TypedMessage messageToPlayers = new TypedMessage("player-exited", u);

        for (User user : m.getPlayers().values()) {
            if (user.getId() != message.getUserId()) {
                MessageBroker.getInstance().sendMessageToUser(user.getId(), gson.toJson(messageToPlayers));
            }
        }

        u.getDeck().clear();
    }

    // Indica que o jogador está pronto para jogar
    public void readyToPlay(ReadyToPlayMessage message) throws InterruptedException {
        readyToPlay(message.getUserId(), message.getMatchId());

        Match match = this.registry.getMatch(message.getMatchId());

        boolean allPlayersReady = true;

        for (User u : match.getPlayers().values()) {
            allPlayersReady = allPlayersReady && u.getStatus() == UserStatus.READY;
        }

        if (allPlayersReady) {
            match.iniciarPartida();

            for (User user : match.getPlayers().values()) {
                TypedMessage messagetoUser = new TypedMessage("match-started", gson.toJson(match));
                MessageBroker.getInstance().sendMessageToUser(user.getId(), gson.toJson(messagetoUser));
            }
            
            MatchRunner runner = new MatchRunner(match);

            Registry.getInstance().addRunner(match.getMatchId(), runner);

            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    runner.run();
                }
            });
        } 
    }

    // Indica que uma carta foi jogada
    public void playCard(PlayCardMessage message) {
        MatchRunner runner = registry.getRunner(message.getMatchId());
        runner.onPlayCardMessage(message);
    }
    
    // Indica que uma carta foi comprada
    public void buyCard(BuyCardMessage message) {
        MatchRunner runner = registry.getRunner(message.getMatchId());
        runner.onBuyCardMessage(message);
    }

    // Indica que o jogo precisou dar ao jogador várias cartas
    public void buyCardAuto(BuyCardAutoMessage message) {
        MatchRunner runner = registry.getRunner(message.getMatchId());
        runner.onBuyCardAutoMessage(message);
    }

    // Indica que um jogador pediu uno
    public void askUno(AskUnoMessage message) {
        MatchRunner runner = registry.getRunner(message.getMatchId());
        runner.onAskUno(message);
    } 

    public String signUp(String username, String password, int avatarId) {
        User user = new User(username, password, this.registry.getAvatar(avatarId));
        this.registry.putUser(user);
        return gson.toJson(user);
    }

    public String login(String username, String password) {
        return gson.toJson(doLogin(username, password));
    }

    private User doLogin(String username, String password) {
        User user = null;

        for (User us : this.registry.getUsersList()) {
            if (us.getName().contentEquals(username)
                    && us.getPassword().contentEquals(password)) {
                user = us;
            }
        }

        return user;
    }

    public String myProfile(int userId) {
        User user = this.registry.getUser(userId);
        return gson.toJson(user);
    }

    public String createMatch(String name, int qtdPlayers) {
        Match match = new Match(name, qtdPlayers);

        this.registry.putMatch(match);

        return gson.toJson(match);
    }

    public String getAvatarsList() {
        return gson.toJson(this.registry.getAvatarsList());
    }

    public String getMatch(int matchId) {
        Match match = this.registry.getMatch(matchId);

        return gson.toJson(match);
    }

    public String getMatchesList() {
        return gson.toJson(this.registry.getMatchesList());
    }

    private String matchesWithPlayer(int userId) {
        return gson.toJson(this.registry.getMatchesWithPlayer(userId));
    }

    private String matchesWinner(int userId) {
        return gson.toJson(this.registry.getWinsPlayer(userId));
    }

    public String joinMatch(int userId, int matchId) {
        User user = this.registry.getUser(userId);
        Match match = this.registry.getMatch(matchId);

        match.addPlayer(user);

        return gson.toJson(match);
    }

    public String quitMatch(int userId, int matchId) {
        this.registry.removePlayerFromMatch(matchId, userId);
        Match match = this.registry.getMatch(matchId);
        User user = match.getPlayers().get(userId);
        
        user.setStatus(UserStatus.UNREADY);

        return gson.toJson(match);
    }

    public String readyToPlay(int userId, int matchId) {
        Match match = this.registry.getMatch(matchId);
        User user = match.getPlayers().get(userId);

        user.setStatus(UserStatus.READY);

        return gson.toJson(match);
    }

    public Collection<User> getUsers() {
        return this.registry.getUsersList();
    }

    public Collection<Match> getMatches() {
        return this.registry.getMatchesList();
    }

}
