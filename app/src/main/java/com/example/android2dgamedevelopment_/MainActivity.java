package com.example.android2dgamedevelopment_;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/*
Main activity is the entry point to our application
 */

public class MainActivity extends Activity {


    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity.java", "onCreate()");
        // Set content view to our new game object
        game = new Game(this);
        setContentView(game);


        }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity.java", "onStart()");

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity.java", "onResume()");
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity.java", "onPause()");
        game.pause();
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity.java", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity.java", "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        Log.d("MainActivity.java", "onBackPressed()");
        // Commenting out the back pressed so it doesn't crash the game
        //super.onBackPressed();
    }
}

