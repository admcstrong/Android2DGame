package com.example.android2dgamedevelopment_.gameobject;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.android2dgamedevelopment_.Gameloop;
import com.example.android2dgamedevelopment_.R;

/**
 * Enemy is a character which always moves in the direction of the player
 * The enemy class is an extension of a Circle, which is an extension of a GameObject
 */
public class Enemy extends Circle {

    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND*0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / Gameloop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = Gameloop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;

    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemy),
                Math.random()*1000,
                Math.random()*1000,
                30
        );
        this.player = player;
    }

    /**
     * readyToSpawn checks if a new enemy should spawn, according to the desired number of spawns
     * per minute (see SPAWNS_PER_MINUTE at top)
     */
    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }
    }

    @Override
    public void update() {
        // ***** Update the velocity of the enemy so it is in the direction of the player

        // Calculate vector from enemy to player (in x and y)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        // Calculate absolute distance between enemy (this) and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        // Calculate direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        // Set velocity in the direction of the player
        if (distanceToPlayer > 0) { //Avoid division by zero
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        // Update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;

    }
}
