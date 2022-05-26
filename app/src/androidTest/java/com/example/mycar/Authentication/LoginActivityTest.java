package com.example.mycar.Authentication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.mycar.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity loginActivity = null;

    @Before
    public void setUp() throws Exception {
        loginActivity = mActivityTestRule.getActivity();
    }
    @Test
    public void testLogin(){
        EditText email = loginActivity.findViewById(R.id.Email);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                email.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("wart_152@mail.ru");
        getInstrumentation().waitForIdleSync();
        assertEquals("wart_152@mail.ru", email.getText().toString());

    }
    @Test
    public void testPassword(){
        EditText password = loginActivity.findViewById(R.id.Password);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                password.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("1234");
        getInstrumentation().waitForIdleSync();
        assertEquals("1234", password.getText().toString());
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }
}