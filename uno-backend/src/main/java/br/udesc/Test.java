package br.udesc;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;

import org.awaitility.*;
import org.awaitility.core.ConditionTimeoutException;

public class Test {

    private static final Random r = new Random();

    public static void main(String[] args) {

        long time = new Date().getTime();
        Awaitility.await().ignoreExceptions().atMost(Duration.ofSeconds(6)).until(isConditionSatisfied());
        long time2 = new Date().getTime();
        System.out.println("Coiseou em " + (time2 - time) / 1000 + "s");

    }

    private static Callable<Boolean> isConditionSatisfied() {
        return () -> {
            int rr = r.nextInt(90);
            System.out.println("Tentou coisear com " + rr);
            return rr == 5;
        };
    }

}
