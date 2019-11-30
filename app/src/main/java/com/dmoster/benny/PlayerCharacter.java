package com.dmoster.benny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.dmoster.benny.BitmapFunctions;

import java.util.HashMap;
import java.util.Map;

public class PlayerCharacter extends Entity {

    BitmapFunctions bitFunc = new BitmapFunctions();

    private Bitmap standBitmap;
    private Bitmap standBitmapMirrored;
    private Bitmap walkBitmap;
    private Bitmap walkBitmapMirrored;
    private Bitmap bulldozeBitmap;
    private Bitmap bulldozeBitmapMirrored;

    Map<String, Integer> frameCount = new HashMap<String,Integer>();

    public PlayerCharacter() {
        super(100, 0, 300, "Default Player");
        frameCount.put("Stand", 5);
        frameCount.put("Walk", 2);
        frameCount.put("Bulldoze", 2);
        Log.e("PLAYER CHARACTER CLASS:", "CALLED DEFAULT CONSTRUCTOR, NO BITMAPS CREATED!");
    }

    public PlayerCharacter(Context c)
    {
        super(100, 300, 300, "Bob");
        isFacingRight = true;
        AddHashMapValues();
        Log.i("PLAYER CHARACTER", "HashMapValues Loaded");
        standBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_stand, frameCount.get("Stand"));
        standBitmapMirrored = bitFunc.FlipBitmap(standBitmap);
        walkBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_walk, frameCount.get("Walk"));
        walkBitmapMirrored = bitFunc.FlipBitmap(walkBitmap);
        bulldozeBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_bulldoze, frameCount.get("Bulldoze"));
        bulldozeBitmapMirrored = bitFunc.FlipBitmap(bulldozeBitmap);
        Log.i("PLAYER CHARACTER", "Hashmaps and Mirrored Hashmaps created");
    }


    public void AddHashMapValues()
    {
        frameCount.put("Stand", 5);
        frameCount.put("Walk", 2);
        frameCount.put("Bulldoze", 2);
    }

    @Override
    public void drawToCanvas(GameView g, Canvas c) {
        whereToDraw.set((int) getXPosition(),
                getYPosition(),
                (int) getXPosition() + bitmapFrameWidth,
                getYPosition() + bitmapFrameHeight);

        if (isFacingRight && isMoving) {
            currentAnimationFrameCount = frameCount.get("Walk");

            g.getCurrentFrame(this);

            c.drawBitmap(walkBitmap,
                    frameToDraw,
                    whereToDraw, g.paint);
        } else if (!isFacingRight && isMoving) {
            currentAnimationFrameCount = frameCount.get("Walk");

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

    @Override
    public void collideEntity(Entity hitTarget) {
        Log.i("PLAYER CHARACTER", "Hit an entity");
        if(hitTarget.name == "Purple Lolly")
        {
            HeadsUpDisplay.getInstance().addLollies(1);
        } else if(hitTarget.name == "Pink Lolly")
        {
            HeadsUpDisplay.getInstance().addLollies(1);
        } else if(hitTarget.name == "Red Lolly")
        {
            HeadsUpDisplay.getInstance().addLollies(1);
        }
    }


}
