package com.example.android2dgamedevelopment_.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.android2dgamedevelopment_.Gameloop;
import com.example.android2dgamedevelopment_.Joystick;
import com.example.android2dgamedevelopment_.R;

/**
 * Player is the main character of the game, which the user can control with a touch joystick
 * The player class is an extention of a Circle, which is an extention of a GameObject

 **/


public class Player extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / Gameloop.MAX_UPS;
    private final Joystick joystick;


    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
    }

    public void update() {
        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
