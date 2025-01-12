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
                //R.drawable.sprite_sheet, bitmapOptions);
                R.drawable.sprite_sheet_96_96pixels, bitmapOptions);
    }

    public Sprite[] getPlayerSpriteArray() {
        Sprite[] spriteArray = new Sprite[3];
        //return new Sprite(this, new Rect(64, 0, 128, 64));
        spriteArray[0] = new Sprite(this, new Rect(96, 0, 192, 96)); // base
        spriteArray[1] = new Sprite(this, new Rect(0, 96, 96, 192)); // moving 1
        spriteArray[2] = new Sprite(this, new Rect(96, 96, 192, 192)); // moving 2
        return spriteArray;
    }

    public Bitmap getBitmap() {
        return bitmap;

    }
}
