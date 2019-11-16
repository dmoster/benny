package com.dmoster.benny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.dmoster.benny.BitmapFunctions;

import java.util.HashMap;
import java.util.Map;

public class PlayerCharacter extends Entity implements IDrawable {

    BitmapFunctions bitFunc = new BitmapFunctions();

    Bitmap standBitmap;
    Bitmap standBitmapMirrored;
    Bitmap walkBitmap;
    Bitmap walkBitmapMirrored;
    Bitmap bulldozeBitmap;
    Bitmap bulldozeBitmapMirrored;

    Map<String, Integer> frameCount = new HashMap<String,Integer>();

    public PlayerCharacter(Context c)
    {
        super(10, 400, 300);
        frameCount.put("Stand", 5);
        frameCount.put("Walk", 2);
        frameCount.put("Bulldoze", 2);

        standBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_stand, frameCount.get("Stand"));
        standBitmapMirrored = bitFunc.FlipBitmap(standBitmap);
        walkBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_walk, frameCount.get("Walk"));
        walkBitmapMirrored = bitFunc.FlipBitmap(standBitmap);
        bulldozeBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_bulldoze, frameCount.get("Bulldoze"));
        bulldozeBitmapMirrored = bitFunc.FlipBitmap(standBitmap);
    }


    public void drawToCanvas(GameView g, Canvas c) {
        if (isFacingRight && isMoving) {
            currentAnimationFrameCount = frameCount.get("Walk");
            whereToDraw.set((int) getXPosition(),
                    400,
                    (int) getXPosition() + bitmapFrameWidth,
                    400 + bitmapFrameHeight);

            g.getCurrentFrame(this);

            c.drawBitmap(walkBitmap,
                    frameToDraw,
                    whereToDraw, g.paint);
        } else if (!isFacingRight && isMoving) {
            currentAnimationFrameCount = frameCount.get("Walk");
            whereToDraw.set((int) getXPosition(),
                    400,
                    (int) getXPosition() + bitmapFrameWidth,
                    400 + bitmapFrameHeight);

            g.getCurrentFrame(this);

            c.drawBitmap(walkBitmapMirrored,
                    frameToDraw,
                    whereToDraw, g.paint);
        }

        else if(!isMoving && isFacingRight)
        {
            currentAnimationFrameCount = frameCount.get("Stand");

            g.getCurrentFrame(this);

            c.drawBitmap(standBitmap,
                    frameToDraw,
                    whereToDraw, g.paint);
        } else if(!isMoving && !isFacingRight) {
            currentAnimationFrameCount = frameCount.get("Stand");

            g.getCurrentFrame(this);

            c.drawBitmap(standBitmapMirrored,
                    frameToDraw,
                    whereToDraw, g.paint);
        }
    }
}
