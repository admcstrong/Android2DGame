package com.example.android2dgamedevelopment_;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

/**
 * Gameover is a panel which draws the game over screen
 */
public class GameOver {

    private Context context;

    public GameOver(Context context) {
        this.context = context;

    }

    public void draw(Canvas canvas) {

        String text = "Game Over";
        float x = 800;
        float y = 200;
        float textSize = 150;
        int color = ContextCompat.getColor(context, R.color.gameOver);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(textSize);

        canvas.drawText(text, x, y, paint);

    }
}
