package com.dmoster.benny;

/**
 * Class for creating dynamically-generated tile maps
 *
 * This class is used to parse an array containing keys to tile types, turning it into a tile map
 * drawn on-screen.
 *
 * @author dmoster
 * @version 2019.1128
 * @since 1.0
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class TileMap {
  private static final String TAG = "TILEMAP";
  public static final int TILE_SIZE = 128;

  private int[][]tileMapData;

  private final Tile[] tiles = {
      new Tile(51, 51, 51, false),
      new Tile(136, 136, 136, false),
      new Tile(85, 85, 85, true),
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

  /**
   * Constructor that loads current map data into the map
   * @param tileMapData Array containing tile type keys for each index on the map
   */
  public TileMap(int[][] tileMapData) {
    this.tileMapData = tileMapData;
  }

  /**
   * Draw method for TileMap
   *
   * This method parses the tileMapData array and draws the appropriate tiles using the Tile class.
   *
   * @param canvas Main canvas of the GameView where the tiles are drawn
   */
  public void draw(Canvas canvas) {
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


  // Map wrapping
  // 1. If user gets to end of screen, send him to other end
  // 2.


  private class Tile {
    private int red;
    private int green;
    private int blue;
    protected boolean solid;

    /**
     * Constructor that initializes appropriate member variables for a new Tile type
     *
     * @param red
     * @param green
     * @param blue
     * @param solid Defines whether or not sprites can pass through this Tile type
     */
    public Tile(int red, int green, int blue, boolean solid) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.solid = solid;
    }

    /**
     * Draw method for Tile
     *
     * @param x Horizontal element of tile coordinate
     * @param y Vertical element of tile coordinate
     * @param canvas Main canvas of the GameView where the tiles are drawn
     */
    public void draw(int x, int y, Canvas canvas) {
      Paint paint = new Paint();
      paint.setColor(Color.rgb(red, green, blue));

      if (solid) {

        canvas.drawRect(new RectF(x - (TILE_SIZE / 2),
            y + (TILE_SIZE / 2),
            x + (TILE_SIZE / 2),
            y - (TILE_SIZE / 2)),
            paint);
      }
      else {
        canvas.drawRect(x - (TILE_SIZE / 2),
            y + (TILE_SIZE / 2),
            x + (TILE_SIZE / 2),
            y - (TILE_SIZE / 2),
            paint);
      }
    }
  }
}
