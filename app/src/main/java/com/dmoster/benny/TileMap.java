package com.dmoster.benny;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class TileMap {
  private static final String TAG = "TILEMAP";
  public static final int TILE_SIZE = 128;

  private int[][]tileMapData;

  private final Tile[] tiles = {
      new Tile(51, 51, 51, false),
      new Tile(136, 136, 136, false),
      new Tile(85, 85, 85, false),
      new Tile(121, 220, 242, false),
      new Tile(119, 119, 119, false),
      new Tile(227, 115, 250, true),
      new Tile(102, 102, 102, true),
      new Tile(115, 198, 250, false),
      new Tile(250, 223, 115, false),
      new Tile(201, 50, 50, false),
      new Tile(85, 85, 85, true),
      new Tile(0, 255, 255, false)
  };

  public TileMap(int[][] tileMapData) {
    this.tileMapData = tileMapData;
  }

  public void draw(Canvas canvas, int width, int height) {
    Log.d(TAG, "TileMap.draw() not yet implemented");
    int tileLocX;
    int tileLocY = 64;

    for (int[] row: tileMapData) {
      tileLocX = 64;
      for (int tileKey: row) {
        tiles[tileKey].draw(tileLocX, tileLocY, canvas);
        tileLocX += 128;
      }
      tileLocY += 128;
    }
  }


  private class Tile {
    private int red;
    private int green;
    private int blue;
    protected boolean solid;

    public Tile(int red, int green, int blue, boolean solid) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.solid = solid;
    }

    public void draw(int x, int y, Canvas canvas) {
      Paint paint = new Paint();
      paint.setColor(Color.rgb(red, green, blue));
      canvas.drawRect(x - (TILE_SIZE / 2),
          y + (TILE_SIZE / 2),
          x + (TILE_SIZE / 2),
          y - (TILE_SIZE / 2),
          paint);
    }
  }
}
