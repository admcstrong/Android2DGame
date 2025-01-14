package com.example.android2dgamedevelopment_.Map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.android2dgamedevelopment_.graphics.SpriteSheet;

abstract class Tile {

    protected final Rect mapLocationRect;

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public enum TileType {
        GREY_STONE_TILE,
        RED_BRICK_TILE,
        GOLDEN_TILE
    }
    public static Tile getTile(int idxTileType, SpriteSheet spriteSheet, Rect mapLocationRect) {
        switch(TileType.values()[idxTileType]) {
            case GREY_STONE_TILE:
                return new GreyStoneTile(spriteSheet, mapLocationRect);
            case RED_BRICK_TILE:
                return new RedBrickTile(spriteSheet, mapLocationRect);
            case GOLDEN_TILE:
                return new GoldenTile(spriteSheet, mapLocationRect);
            default:
                return null;
        }
    }

    public abstract void draw(Canvas canvas);
}
