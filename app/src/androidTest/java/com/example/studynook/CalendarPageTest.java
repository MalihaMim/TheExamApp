package com.example.studynook;

import android.app.Instrumentation;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.google.android.material.internal.ContextUtils.getActivity;
import static org.junit.Assert.*;

public class CalendarPageTest {
    private String stringToBetyped;

    @Rule
    public ActivityScenarioRule<CalendarPage> scenarioRule =
            new ActivityScenarioRule<CalendarPage>(CalendarPage.class);

    public ActivityScenario<CalendarPage> calendarPage = null;

    @Before
    public void setUp() throws Exception {
        stringToBetyped = "Testing";
    }

    @Test
    public void clickButton_sameActivity () {
        // Type text and then press the button.
        onView(withId(R.id.userInput))
                .perform(typeText(stringToBetyped), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.viewEvent)).perform(click());

    }

    @After
    public void tearDown() throws Exception {
    }
}