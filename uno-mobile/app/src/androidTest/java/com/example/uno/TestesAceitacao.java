package com.example.uno;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.uno.model.Avatar;
import com.example.uno.model.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestesAceitacao {

    private User usuario;

    @Rule
    public ActivityScenarioRule<TelaInicial> telaInicial
            = new ActivityScenarioRule<TelaInicial>(TelaInicial.class);

    @Before
    public void antes() {
        this.usuario = new User("Luis", "1234", new Avatar("avatar_1"));
    }

    @Test
    public void testInServer() {
        //Realiza login
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText(this.usuario.getName()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(this.usuario.getPassword()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(ViewActions.click());

        //Entra no servidor
        onView(withId(R.id.edIP)).perform(ViewActions.typeText("127.0.0.1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edPorta)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrarServidor)).perform(ViewActions.click());

        //Entra na partida
        onView(withId(R.id.icEntrarJogo)).perform(ViewActions.click());

        //Verifica se o usuário está na sala
        onView(withId(R.id.icPedirUno)).check(matches(isDisplayed()));
    }

    @Test
    public void testTurnos() {
        //Realiza login
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText(this.usuario.getName()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(this.usuario.getPassword()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(ViewActions.click());

        //Entra no servidor
        onView(withId(R.id.edIP)).perform(ViewActions.typeText("127.0.0.1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edPorta)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrarServidor)).perform(ViewActions.click());

        //Entra na partida
        onView(withId(R.id.icEntrarJogo)).perform(ViewActions.click());

        //Comprar uma carta
        onView(withId(R.id.imgMonte)).perform(ViewActions.click());

        //Verificar se é possível comprar carta novamente
        onView(withId(R.id.imgMonte)).check(matches(isDisplayed()));
    }

    @Test
    public void testRivalCards() {
        //Realiza login
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText(this.usuario.getName()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(this.usuario.getPassword()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(ViewActions.click());

        //Entra no servidor
        onView(withId(R.id.edIP)).perform(ViewActions.typeText("127.0.0.1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edPorta)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrarServidor)).perform(ViewActions.click());

        //Entra na partida
        onView(withId(R.id.icEntrarJogo)).perform(ViewActions.click());

        //Verifica se há a informação das cartas dos oponente
        onView(withId(R.id.listaJogadoresJogo)).check(matches(isDisplayed()));
    }

}
