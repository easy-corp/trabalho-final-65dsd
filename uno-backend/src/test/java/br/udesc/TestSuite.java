package br.udesc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    AvatarListTest.class,
    ListMatchesTest.class,
    LoginTest.class,
    MatchLifecycleTest.class,
    SignUpTest.class,
})

public class TestSuite {
}
