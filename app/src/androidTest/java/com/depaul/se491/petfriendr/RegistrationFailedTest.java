package com.depaul.se491.petfriendr;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistrationFailedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void registrationFailureTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.createaccount), withText("Create Account"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.DrawerLayout),
                                                1)),
                                2),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.DrawerLayout),
                                                1)),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("solisjessica"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.inputEmail),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.DrawerLayout),
                                                1)),
                                2),
                        isDisplayed()));
        editText2.perform(replaceText("solisjessica@depaul.edu"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.DrawerLayout),
                                                1)),
                                3),
                        isDisplayed()));
        editText3.perform(replaceText("short"), closeSoftKeyboard());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.createAccountButton), withText("Create Account"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.DrawerLayout),
                                                1)),
                                4),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.createAccountButton), withText("CREATE ACCOUNT"),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(R.id.DrawerLayout)))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.inputPassword), withText("•••••"),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(R.id.DrawerLayout)))),
                        isDisplayed()));
        editText4.check(matches(withText("•••••")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
