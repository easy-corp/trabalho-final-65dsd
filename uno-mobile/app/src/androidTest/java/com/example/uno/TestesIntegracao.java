package com.example.uno;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.uno.model.Avatar;
import com.example.uno.model.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestesIntegracao {

    private User usuario;

    @Rule
    public ActivityScenarioRule<TelaInicial> telaInicial
            = new ActivityScenarioRule<TelaInicial>(TelaInicial.class);

    @Before
    public void antes() {
        this.usuario = new User("Luis", "1234", new Avatar("usuario_1"));

        //Se conecta a um servidor
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText("127.0.0.1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText("80"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnConectar)).perform(ViewActions.click());

        //Realiza o login
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText(this.usuario.getName()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(this.usuario.getPassword()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnEntrar)).perform(ViewActions.click());
    }

    @Test
    public void testShowInfos() {
        //Abre a tela de perfil
        onView(withId(R.id.icUsuario)).perform(ViewActions.click());

        //Verifica se o nome do usuário logado é exibido
        onView(withText(this.usuario.getName())).check(matches(isDisplayed()));
    }

    @Test
    public void testShowJogos() {
        //Verifica se há jogos na tela
        onView(withId(R.id.listaJogos)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowJogadores() {
        //Entra em um jogo
        onView(withId(R.id.icEntrarJogo)).perform(ViewActions.click());

        //Verifica se há jogadores no jogo
        onView(withId(R.id.listaJogadoresJogo)).check(matches(isDisplayed()));
    }

}
