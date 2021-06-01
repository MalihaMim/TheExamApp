package com.example.studynook;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SignUpPageTest {

    private String nameToBetyped, emailToBetyped, pwToBetyped;

    @Rule
    public ActivityScenarioRule<SignUpPage> activityRule
            = new ActivityScenarioRule<>(SignUpPage.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        nameToBetyped = "test";
        emailToBetyped = "test";
        pwToBetyped = "12345678";
    }

    @Test
    public void hasErrorText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.regName))
                .perform(typeText(nameToBetyped), closeSoftKeyboard());
        onView(withId(R.id.regEmail))
                .perform(typeText(emailToBetyped), closeSoftKeyboard());
        onView(withId(R.id.regPassword))
                .perform(typeText(pwToBetyped), closeSoftKeyboard());
        onView(withId(R.id.regAccountButton)).perform(click());

        // Check that the text has error message.
        onView(withId(R.id.regEmail))
                .check(matches(hasErrorText("Enter a valid email")));
    }
}