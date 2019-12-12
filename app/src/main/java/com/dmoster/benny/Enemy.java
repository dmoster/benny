package com.dmoster.benny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;



public class Enemy extends Entity {
  /**
   * Constructor that creates a new entity, this should never be called on its own.
   *
   * @param xPos          the starting x position of the entity stored in an int
   * @param yPos          the starting y position of the entity stored in an int
   * @param movementSpeed the speed that which a unit will move across the screen. 0 means the unit is stationary.
   * @param name          the name of the entity, just so its easier to find and log info.
   */

  private static final String TAG = "ENEMY";

  BitmapFunctions bitFunc = new BitmapFunctions();

  private Bitmap enemyBitmap;
  private Bitmap enemyBitmapMirrored;

  private static final int frameCount = 2;



  public Enemy(Context c, int xPos, int yPos, int movementSpeed, String name) {
    super(xPos, yPos, movementSpeed, name);
    isFacingRight = false;

    if (name == "Old Lady") {
     // enemyBitmap = bitFunc.LoadBitmap(this, c, R.drawable.old_lady, frameCount);
    }
    else if (name == "Hipster") {
      enemyBitmap = bitFunc.LoadBitmap(this, c, R.drawable.hipster, frameCount);
    }


    enemyBitmapMirrored = bitFunc.FlipBitmap(enemyBitmap);
    Log.i(TAG, "Enemy created...");
  }

  @Override
  public void drawToCanvas(GameView g, Canvas c) {
    whereToDraw.set((int) getXPosition(),
        getYPosition(),
        (int) getXPosition() + bitmapFrameWidth,
        getYPosition() + bitmapFrameHeight);

    if (isFacingRight && isMoving) {
      currentAnimationFrameCount = frameCount;

      g.getCurrentFrame(this);

      c.drawBitmap(enemyBitmap,
          frameToDraw,
          whereToDraw, g.paint);
    } else if (!isFacingRight && isMoving) {
      currentAnimationFrameCount = frameCount;

      g.getCurrentFrame(this);

      c.drawBitmap(enemyBitmapMirrored,
          frameToDraw,
          whereToDraw, g.paint);
    }

//    else if(!isMoving && isFacingRight)
//    {
//      currentAnimationFrameCount = frameCount.get("Stand");
//
//      g.getCurrentFrame(this);
//
//      c.drawBitmap(standBitmap,
//          frameToDraw,
//          whereToDraw, g.paint);
//    } else if(!isMoving && !isFacingRight) {
//      currentAnimationFrameCount = frameCount.get("Stand");
//
//      g.getCurrentFrame(this);
//
//      c.drawBitmap(standBitmapMirrored,
//          frameToDraw,
//          whereToDraw, g.paint);
//    }
  }

  @Override
  public void collideEntity(Entity hitTarget, Context context) {

  }


  public void kill() {
    alive = false;
  }
}
