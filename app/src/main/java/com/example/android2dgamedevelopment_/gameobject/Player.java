package com.example.android2dgamedevelopment_.gameobject;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.android2dgamedevelopment_.GameDisplay;
import com.example.android2dgamedevelopment_.Gameloop;
import com.example.android2dgamedevelopment_.gamepanel.HealthBar;
import com.example.android2dgamedevelopment_.gamepanel.Joystick;
import com.example.android2dgamedevelopment_.R;
import com.example.android2dgamedevelopment_.Utils;
import com.example.android2dgamedevelopment_.graphics.AnimatorRaw;
import com.example.android2dgamedevelopment_.graphics.Sprite;

/**
 * Player is the main character of the game, which the user can control with a touch joystick
 * The player class is an extention of a Circle, which is an extention of a GameObject

 **/


public class Player extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / Gameloop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 10;
    private final Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;
    private Sprite sprite;
    private AnimatorRaw animatorRaw;
    private PlayerState playerState;


    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius, AnimatorRaw animatorRaw) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.healthPoints = MAX_HEALTH_POINTS;
        this.animatorRaw = animatorRaw;
        this.playerState = new PlayerState(this);
        //this.sprite = sprite;
    }

    public void update() {
        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }

        playerState.update();
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animatorRaw.draw(canvas, gameDisplay, this);
        //super.draw(canvas, gameDisplay);
        healthBar.draw(canvas, gameDisplay);

    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if (healthPoints >= 0)
            this.healthPoints = healthPoints;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
