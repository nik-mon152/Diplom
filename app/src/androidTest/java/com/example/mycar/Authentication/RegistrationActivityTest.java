package com.example.mycar.Authentication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.mycar.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegistrationActivityTest {
    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityTestRule = new ActivityTestRule<RegistrationActivity>(RegistrationActivity.class);
    private RegistrationActivity registrationActivity = null;

    @Before
    public void setUp() throws Exception {
        registrationActivity = mActivityTestRule.getActivity();
    }
    @Test
    public void testName(){
        EditText name = registrationActivity.findViewById(R.id.FirstName);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                name.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Nikita");
        getInstrumentation().waitForIdleSync();
        assertEquals("Nikita", name.getText().toString());

    }
    @Test
    public void testLastName(){
        EditText last = registrationActivity.findViewById(R.id.LastName);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                last.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Monakhov");
        getInstrumentation().waitForIdleSync();
        assertEquals("Monakhov", last.getText().toString());

    }
    @Test
    public void testPatronymic(){
        EditText patronymic = registrationActivity.findViewById(R.id.patronymic);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                patronymic.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Yurievich");
        getInstrumentation().waitForIdleSync();
        assertEquals("Yurievich", patronymic.getText().toString());

    }
    @After
    public void tearDown() throws Exception {
        registrationActivity = null;
    }

}