package br.udesc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    AvatarListTest.class,
    MatchesListTest.class,
    LoginTest.class,
    MatchLifecycleTest.class,
    ReadyToPlayTest.class,
    SignUpTest.class,
})

public class TestSuite {
}
