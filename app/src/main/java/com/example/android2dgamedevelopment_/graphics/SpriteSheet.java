package com.example.android2dgamedevelopment_.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.android2dgamedevelopment_.R;

public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false; //Why is this?
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                 R.drawable.sprite_sheet, bitmapOptions);
    }

    public Sprite getPlayerSprite() {
        return new Sprite(this, new Rect(64, 0, 128, 64));
    }

    public Bitmap getBitmap() {
        return bitmap;

    }
}
