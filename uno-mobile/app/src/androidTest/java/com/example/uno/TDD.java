package com.example.uno;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.espresso.action.ViewActions;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.uno.model.Avatar;
import com.example.uno.model.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TDD {

    private User usuario;

    @Rule
    public ActivityScenarioRule<TelaInicial> telaInicial
            = new ActivityScenarioRule<TelaInicial>(TelaInicial.class);

    @Before
    public void antes() {
        //Cria um usuário
        this.usuario = new User("Luis", "1234", new Avatar("avatar_1"));

        //Realiza login
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText(this.usuario.getName()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(this.usuario.getPassword()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(ViewActions.click());

        //Entra no servidor
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText("127.0.0.1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrarServidor)).perform(ViewActions.click());

        //Entra na partida
        onView(withId(R.id.icEntrarJogo)).perform(ViewActions.click());
    }

    @Test
    public void testDescarte() {
        //Tenta descartar uma carta
        onView(withId(R.id.listaCartasJogador)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.imgCarta)));
    }

    @Test
    public void testCompra() {
        //Tenta comprar uma carta
        onView(withId(R.id.imgMonte)).perform(ViewActions.click());
    }

    @Test
    public void testResult() {
        //Finaliza o jogo
        onView(withId(R.id.icSairJogo)).perform(ViewActions.click());

        //Verifica se o vencedor está ali
        onView(withId(R.id.imgVencedorAvatar)).check(matches(isDisplayed()));
        onView(withId(R.id.txtVencedorNome)).check(matches(isDisplayed()));

        //Verifica se os jogadores estão ali
        onView(withId(R.id.imgVencedorAvatar)).check(matches(isDisplayed()));
        onView(withId(R.id.listaJogadoresResult)).check(matches(isDisplayed()));
    }

}
