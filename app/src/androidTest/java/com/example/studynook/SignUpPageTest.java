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

    private String stringToBetyped;

    @Rule
    public ActivityScenarioRule<SignUpPage> activityRule
            = new ActivityScenarioRule<>(SignUpPage.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "test";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.regEmail))
                .perform(typeText(stringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.regAccountButton)).perform(click());

        // Check that the text has error message.
        onView(withId(R.id.regEmail))
                .check(matches(hasErrorText("Enter a valid email")));
    }
}