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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import com.example.studynook.StopwatchPage;
import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class StopwatchPageTest {

    private static String stringToBeTyped;

    @Rule
    public ActivityScenarioRule<StopwatchPage> activityRule = new ActivityScenarioRule<>(StopwatchPage.class);

    @Before
    public void initValidString() throws NoSuchFieldException, IllegalAccessException {
        stringToBeTyped = "00 : 00 : 57";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
//        onView(withId(R.id.chronometer))
//                .perform(typeText(stringToBeTyped), closeSoftKeyboard());
        onView(withId(R.id.start)).perform(click());
        onView(withId(R.id.start)).perform(click());

        // Check that the text has error message.
        onView(withId(R.id.chronometer))
                .check(matches(withText(stringToBeTyped)));
    }
}