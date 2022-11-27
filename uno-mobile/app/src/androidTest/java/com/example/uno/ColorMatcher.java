package com.example.uno;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ColorMatcher {

    private ColorMatcher(){}

    public static Matcher<View> withTextColor(@ColorInt final int expectedColor) {
        return new BoundedMatcher(TextView.class) {
            int currentColor = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("expected TextColor: ")
                        .appendValue(Integer.toHexString(expectedColor));
                description.appendText(" current TextColor: ")
                        .appendValue(Integer.toHexString(currentColor));
            }

            @Override
            protected boolean matchesSafely(Object item) {
                TextView itemTxt = (TextView) item;

                if(currentColor == 0) {
                    currentColor =  itemTxt.getCurrentTextColor();
                }

                return currentColor == expectedColor;
            }
        };
    }
}