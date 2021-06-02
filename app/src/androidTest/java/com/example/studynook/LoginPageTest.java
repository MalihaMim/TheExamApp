package com.example.studynook;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginPageTest {

    private String emailToBetyped, pwToBetyped;

    @Rule
    public ActivityScenarioRule<LoginPage> activityRule
            = new ActivityScenarioRule<>(LoginPage.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        emailToBetyped = "test@gmail.com";
        pwToBetyped = "123456";
    }

    @Test
    public void hasErrorText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.email))
                .perform(typeText(emailToBetyped), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(pwToBetyped), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        // Check that the text has error message.
        onView(withId(R.id.password))
                .check(matches(hasErrorText("Password has to be 8 or more characters")));
    }
}