package br.udesc;

import java.rmi.server.ServerCloneException;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;

import org.awaitility.*;
import org.awaitility.core.ConditionTimeoutException;

import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.server.MatchRunner;
import br.udesc.core.server.Registry;
import br.udesc.core.server.Server;
import br.udesc.core.server.ServerController;

public class Test {

    private static final Random r = new Random();

    public static void main(String[] args) throws InterruptedException {

        // long time = new Date().getTime();
        // Awaitility.await().ignoreExceptions().atMost(Duration.ofSeconds(6)).until(isConditionSatisfied());
        // long time2 = new Date().getTime();
        // System.out.println("Coiseou em " + (time2 - time) / 1000 + "s");

        User us1 = new User("Luis", "1234", null);
        User us2 = new User("Murilo", "1234", null);
        User us3 = new User("Gabriel", "1234", null);

        Match match = new Match("Partida de Teste", 4);
        match.addPlayer(us1);
        match.addPlayer(us2);
        // match.addPlayer(us3);

        Server.getInstance().bindUser(us3, null);

        MatchRunner runner = new MatchRunner(match);
        runner.start();
        runner.join();

    }

    private static Callable<Boolean> isConditionSatisfied() {
        return () -> {
            int rr = r.nextInt(90);
            System.out.println("Tentou coisear com " + rr);
            return rr == 5;
        };
    }

}
