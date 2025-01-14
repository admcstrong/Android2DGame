package com.example.android2dgamedevelopment_.Map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.android2dgamedevelopment_.graphics.Sprite;
import com.example.android2dgamedevelopment_.graphics.SpriteSheet;

public class GoldenTile extends Tile {
    private final Sprite sprite;

    public GoldenTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGoldenSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas,
                mapLocationRect.left,
                mapLocationRect.top
        );
    }
}
