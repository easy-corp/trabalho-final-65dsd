package com.example.uno;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.graphics.Color;

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
public class TestesSistema {

    private User usuario;

    @Rule
    public ActivityScenarioRule<TelaInicial> telaInicial
            = new ActivityScenarioRule<TelaInicial>(TelaInicial.class);

    @Before
    public void antes() {
        this.usuario = new User("Luis", "1234", new Avatar("usuario_1"));
    }

    @Test
    public void testLogin() {
        //Preenche os dados para login
        onView(withId(R.id.edUsuario)).perform(ViewActions.typeText(this.usuario.getName()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(this.usuario.getPassword()), ViewActions.closeSoftKeyboard());

        //Aperta no botão de login
        onView(withId(R.id.btnEntrar)).perform(ViewActions.click());

        //Verifica se a Tela de Servidores foi aberta
        //Indicando que o login foi feito
        onView(withId(R.id.btnEntrarServidor)).check(matches(isDisplayed()));
    }

    @Test
    public void testAskUno() {
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

        //Pede UNO
        onView(withId(R.id.icPedirUno)).perform(ViewActions.click());

        //Verifica se o texto de pedir uno ficou vermelho
        //Color.parseColor("#ED1C24")
        onView(withId(R.id.txtPedirUno)).check(matches(ColorMatcher.withTextColor(Color.parseColor("#ED1C24"))));
    }

    @Test
    public void testPlay() {
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

        //Verifica se e possível clicar para comprar e descartar cartas
        //Isso significa que o jogo está rolando
        onView(withId(R.id.imgMonte)).perform(ViewActions.click());
        onView(withId(R.id.listaCartasJogador)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.imgCarta)));
    }

}
