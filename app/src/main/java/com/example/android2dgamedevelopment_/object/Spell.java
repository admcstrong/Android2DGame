package com.example.android2dgamedevelopment_.object;

import android.content.Context;
import androidx.core.content.ContextCompat;

import com.example.android2dgamedevelopment_.Gameloop;
import com.example.android2dgamedevelopment_.R;

public class Spell extends Circle {

    private final Player spellcaster;
    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / Gameloop.MAX_UPS;

    public Spell(Context context, Player spellcaster) {
        super(
                context,
                ContextCompat.getColor(context, R.color.spell),
                spellcaster.getPositionX(),
                spellcaster.getPositionY(),
                20
        );
        this.spellcaster = spellcaster;

        velocityX = spellcaster.getDirectionX()*MAX_SPEED;
        velocityY = spellcaster.getDirectionY()*MAX_SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
