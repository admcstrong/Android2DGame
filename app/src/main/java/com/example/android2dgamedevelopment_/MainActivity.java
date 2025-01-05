package com.example.android2dgamedevelopment_;

import android.app.Activity;
import android.os.Bundle;

/*
Main activity is the entry point to our application
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view to our new game object
        setContentView(new Game(this));
    }
}

