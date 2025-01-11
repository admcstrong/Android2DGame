package com.example.android2dgamedevelopment_;

import static androidx.core.content.ContextCompat.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.android2dgamedevelopment_.gameobject.Enemy;
import com.example.android2dgamedevelopment_.gameobject.Player;
import com.example.android2dgamedevelopment_.gameobject.Circle;
import com.example.android2dgamedevelopment_.gameobject.Spell;
import com.example.android2dgamedevelopment_.gamepanel.GameOver;
import com.example.android2dgamedevelopment_.gamepanel.Joystick;
import com.example.android2dgamedevelopment_.gamepanel.Performance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private Gameloop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private Context context;
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        gameLoop = new Gameloop(this, surfaceHolder);

        // Intialize game panels
        gameOver = new GameOver(getContext());
        joystick = new Joystick(275, 700, 70, 40);
        performance = new Performance(context, gameLoop);

        // Initialize game objects
        player = new Player(getContext(), joystick, 500, 500, 30);

        // Initialize the game display and center around it
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);


        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle touch event actions
        switch (event.getActionMasked()) { // (a) To reinstate bug change this back to getAction() and ...
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:    // (b) ... remove this line as well. And ...
                if (joystick.getIsPressed()) {
                    // Joystick was pressed before this event --> cast spell
                      ///spellList.add(new Spell(getContext(), player));
                    numberOfSpellsToCast++;
                } else if(joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is pressed in this event -> setIsPressed(true) and Store ID
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    // Joystick was not previously, and is not pressed in this event -> cast spell
                    numberOfSpellsToCast++;
                    ///spellList.add(new Spell(getContext(), player));
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                // Joystick was pressed previously and is now moved
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:  // (c) ... remove this line too ...
                if(joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // Joystick was let go of -> setIsPressed(false) and resetActuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();

                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");


        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new Gameloop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed)");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        // Draw game objects
        player.draw(canvas, gameDisplay);

            // Update status of each enemy
        for (Enemy enemy : enemyList) {
            enemy.draw(canvas, gameDisplay);
        }
           // Update state of each spell
        for (Spell spell : spellList) {
            spell.draw(canvas, gameDisplay);
        }

        // Draw game panels
        joystick.draw(canvas);
        performance.draw(canvas);

          // Draw game over if the player is dead
        if (player.getHealthPoints() <= 0) {
            gameOver.draw(canvas);
        }
    }



    public void update() {

        // Stop updating the game if the player is dead
        if (player.getHealthPoints() <= 0) {
            return;
        }

        // update game state
        joystick.update();
        player.update();

        // Spawn enemy if it is time to spawn new enemies
        while (numberOfSpellsToCast > 0 ) {
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast--;
        }
        if (Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        // Update state of each enemy
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        // Update state of each spell
        for (Spell spell: spellList) {
            spell.update();
        }

        // Iterate through enemyList and check for collision between each enemy and the player
        //.    and all spells
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player))
            {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // remove spell if it collides with the enemy
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
            }
        }

        }
        gameDisplay.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}

