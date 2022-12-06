package br.udesc.core.server;

import java.time.Duration;
import java.util.concurrent.Callable;

import br.udesc.core.model.Match;
import br.udesc.core.server.messages.PlayCardMessage;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;

public class MatchRunner extends Thread{

    private Registry registry = Registry.getInstance();
    private Match match;
    private boolean matchRunning = true;
    private boolean clockWise = true;

    private PlayCardMessage lastPlayCardMessage;

    public MatchRunner(Match match) {
        this.match = match;
    }

    @Override
    public void run() {
        while (matchRunning) {
            
            //your turn joãozinho
            try {
                Awaitility.await().atMost(Duration.ofSeconds(30)).until(doesPlayerPlayCard(2));
            } catch (ConditionTimeoutException e) {
                //Pula pro próximo
            }


        }
    }

    public void onPlayCardMessage(PlayCardMessage message){
        this.lastPlayCardMessage = message;
    }

    private Callable<Boolean> doesPlayerPlayCard(int playerId){
        return () -> {
            if(lastPlayCardMessage != null){
                return lastPlayCardMessage.getUserId() == playerId;
            }
            return false;
        };
    }

    
}
