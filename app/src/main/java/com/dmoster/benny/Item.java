package com.dmoster.benny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Item extends Entity {
  private static final String TAG = "ITEM";

  BitmapFunctions bitFunc = new BitmapFunctions();

  private static Bitmap itemBitmap;
  private static final int frameCount = 2;

  public Item(Context context, int xPos, int yPos, int movementSpeed, int bitmapId, String name) {
    super(xPos, yPos, movementSpeed, name);
    isFacingRight = true;
    isMoving = false;

    itemBitmap = bitFunc.LoadBitmap(this, context, bitmapId, frameCount);

    Log.i(TAG, name + " loaded");
  }

  @Override
  public void drawToCanvas(GameView gameView, Canvas canvas) {
    whereToDraw.set((int) getXPosition(),
                    getYPosition(),
              (int) getXPosition() + bitmapFrameWidth,
            getYPosition() + bitmapFrameHeight);

    if(!isMoving && isFacingRight)
    {
      currentAnimationFrameCount = frameCount;

      gameView.getCurrentFrame(this);

      canvas.drawBitmap(itemBitmap,
          frameToDraw,
          whereToDraw, gameView.paint);
    }
  }

  @Override
  public void collideEntity(Entity hitTarget) {

  }
}
