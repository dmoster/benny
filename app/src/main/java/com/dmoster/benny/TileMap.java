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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.util.HashSet;
import java.util.Set;

public class TileMap {
  public Set<RectF> collisionData = new HashSet<>();

  private static final String TAG = "TILEMAP";
  private static final int TILE_SIZE = 128;

  private int[][][] tileMapData;
  private int [][] currentMap;
  private int mapCounter = 0;

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

  public void setCurrentMap(int mapIndex) {
    mapCounter = mapIndex;
    currentMap = tileMapData[mapCounter];
    collisionData = new HashSet<>();
  }

  /**
   * Constructor that loads current map data into the map
   * @param tileMapData Array containing tile type keys for each index on the map
   */
  public TileMap(int[][][] tileMapData) {
    this.tileMapData = tileMapData;
    this.currentMap = tileMapData[mapCounter];
  }

  /**
   * Draw method for TileMap
   *
   * This method parses the tileMapData array and draws the appropriate tiles using the Tile class.
   *
   * @param canvas Main canvas of the GameView where the tiles are drawn
   */
  public void draw(Canvas canvas, PlayerCharacter player, int width, int height) {
    wrapMap(player, width, height);

    int tileLocX;
    int tileLocY = 64;

    for (int[] row: currentMap) {
      tileLocX = 64;
      for (int tileKey: row) {

        tiles[tileKey].draw(tileLocX, tileLocY, canvas);

        tileLocX += 128;
      }
      tileLocY += 128;
    }
  }


  private void wrapMap(PlayerCharacter player, int width, int height) {
    if (player.getXPosition() > width && mapCounter < tileMapData.length - 1) {
      setCurrentMap(++mapCounter);
      player.setXPosition(0);
    }
    else if (player.getXPosition() > width && mapCounter == tileMapData.length - 1) {
      player.setXPosition(width);
    }
    else if (player.getXPosition() < 0 && mapCounter > 0) {
      setCurrentMap(--mapCounter);
      player.setXPosition(width);
    }
    else if (player.getXPosition() < 0 && mapCounter == 0) {
      player.setXPosition(0);
    }

    if (player.getYPosition() > height) {
      player.kill();
    }
  }


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
        collisionData.add(new RectF(x - (TILE_SIZE / 2),
                        y - (TILE_SIZE / 2),
                        x + (TILE_SIZE / 2),
                        y + (TILE_SIZE / 2)));
        canvas.drawRect(new RectF(x - (TILE_SIZE / 2),
                        y - (TILE_SIZE / 2),
                        x + (TILE_SIZE / 2),
                        y + (TILE_SIZE / 2)),
                        paint);
      }
      else {
        canvas.drawRect(x - (TILE_SIZE / 2),
            y - (TILE_SIZE / 2),
            x + (TILE_SIZE / 2),
            y + (TILE_SIZE / 2),
            paint);
      }
    }
  }
}
