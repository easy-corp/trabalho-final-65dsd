package com.example.uno;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestesUnitarios {

    @Rule
    public ActivityScenarioRule<TelaCadastro> telaCadastro
            = new ActivityScenarioRule<TelaCadastro>(TelaCadastro.class);

    @Test
    public void testSemSenha() {
        //Variáveis
        String nome = "Usuario Qualquer";

        //Preenche o nome e fecha o teclado
        onView(withId(R.id.edNome)).perform(ViewActions.typeText(nome), ViewActions.closeSoftKeyboard());

        //Clica no botão de confirmação
        onView(withId(R.id.btnCadastrar)).perform(ViewActions.click());

        //Verica se permaneceu na tela de cadastro
        //Isso significa que o cadastro não foi feito
        onView(withId(R.id.btnCadastrar)).check(matches(isDisplayed()));

//        onView(withId(R.id.edNome)).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

    @Test
    public void testSenhasDiferentes() {
        //Variáveis
        String nome = "Usuario Qualquer";
        String senha = "1234";
        String confSenha = "4321";

        //Preenche o nome e fecha o teclado
        onView(withId(R.id.edNome)).perform(ViewActions.typeText(nome), ViewActions.closeSoftKeyboard());

        //Preenche a senha e fecha o teclado
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(senha), ViewActions.closeSoftKeyboard());

        //Preenche a confirmação de senha e fecha o teclado
        onView(withId(R.id.edConfSenha)).perform(ViewActions.typeText(confSenha), ViewActions.closeSoftKeyboard());

        //Clica no botão de confirmação
        onView(withId(R.id.btnCadastrar)).perform(ViewActions.click());

        //Verica se permaneceu na tela de cadastro
        //Isso significa que o cadastro não foi feito
        onView(withId(R.id.btnCadastrar)).check(matches(isDisplayed()));

//        onView(withId(R.id.edNome)).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

    @Test
    public void testSenhasIguais() {
        //Variáveis
        String nome = "Usuario Qualquer";
        String senha = "1234";
        String confSenha = "1234";

        //Preenche o nome e fecha o teclado
        onView(withId(R.id.edNome)).perform(ViewActions.typeText(nome), ViewActions.closeSoftKeyboard());

        //Preenche a senha e fecha o teclado
        onView(withId(R.id.edSenha)).perform(ViewActions.typeText(senha), ViewActions.closeSoftKeyboard());

        //Preenche a confirmação de senha e fecha o teclado
        onView(withId(R.id.edConfSenha)).perform(ViewActions.typeText(confSenha), ViewActions.closeSoftKeyboard());

        //Clica no botão de confirmação
        onView(withId(R.id.btnCadastrar)).perform(ViewActions.click());

        //Verica se foi para a tela inicial
        //Isso significa que o cadastro foi feito
        onView(withId(R.id.btnEntrar)).check(matches(isDisplayed()));

//        onView(withId(R.id.edNome)).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

}
