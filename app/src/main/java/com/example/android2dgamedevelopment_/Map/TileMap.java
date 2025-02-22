package com.example.android2dgamedevelopment_.Map;

import com.example.android2dgamedevelopment_.GameDisplay;
import com.example.android2dgamedevelopment_.Map.MapLayout;
import static com.example.android2dgamedevelopment_.Map.MapLayout.NUMBER_OF_COLUMN_TILES;
import static com.example.android2dgamedevelopment_.Map.MapLayout.NUMBER_OF_ROW_TILES;
import static com.example.android2dgamedevelopment_.Map.MapLayout.TILE_HEIGHT_PIXELS;
import static com.example.android2dgamedevelopment_.Map.MapLayout.TILE_WIDTH_PIXELS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.android2dgamedevelopment_.graphics.Sprite;
import com.example.android2dgamedevelopment_.graphics.SpriteSheet;

public class TileMap {

    private final MapLayout mapLayout;
    private Tile[][] tilemap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;

    public TileMap(SpriteSheet spriteSheet) {
        mapLayout = new MapLayout();
        this.spriteSheet = spriteSheet;
        initializeTilemap();
    }

    private void initializeTilemap() {
        int[][] layout = mapLayout.getLayout();
        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow ++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol ++) {
                tilemap[iRow][iCol] = Tile.getTile(
                        layout[iRow][iCol],
                        spriteSheet,
                        getRectByIndex(iRow, iCol)
                        );
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888; //Alpha red green blue (ARBG), 8 bits each
        mapBitmap = Bitmap.createBitmap(
            NUMBER_OF_COLUMN_TILES*TILE_WIDTH_PIXELS,
            NUMBER_OF_ROW_TILES*TILE_HEIGHT_PIXELS,
                config
        );
        Canvas mapCanvas = new Canvas(mapBitmap);

        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow ++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol ++) {
                tilemap[iRow][iCol].draw(mapCanvas
                );
            }
        }

        System.out.println();
    }

    private Rect getRectByIndex(int idxRow, int idxCol) {
        return new Rect(
                idxCol*TILE_WIDTH_PIXELS,
                idxRow*TILE_HEIGHT_PIXELS,
                (idxCol*TILE_WIDTH_PIXELS) + TILE_WIDTH_PIXELS,
                (idxRow*TILE_HEIGHT_PIXELS) + TILE_HEIGHT_PIXELS
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(
                mapBitmap,
                gameDisplay.getGameRect(),
                gameDisplay.DISPLAY_RECT,
                null
        );
    }
}
