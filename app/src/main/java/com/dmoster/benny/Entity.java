package com.dmoster.benny;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

public abstract class Entity {

    String name = "Unnamed Entity";

    int bitmapFrameHeight = 256;
    int bitmapFrameWidth = 256;

    public boolean isMoving;
    public boolean isFacingRight;

    private float XPosition;
    private float YPosition;

    RectF whereToDraw;
    Rect frameToDraw;

    int currentFrame = 0;
    int currentAnimationFrameCount = 0;

    float movementSpeed;

    public Entity(int xPos, int yPos, int movementSpeed, String name)
    {
        this.name = name;
        setXPosition(xPos);
        setYPosition(yPos);
        whereToDraw = new RectF(getXPosition(), 400, getXPosition() + bitmapFrameWidth, bitmapFrameHeight + 400);
        frameToDraw = new Rect(0,0,bitmapFrameWidth, bitmapFrameHeight);
        this.movementSpeed = movementSpeed;
    }

    public float getXPosition() {
        return XPosition;
    }

    public float getYPosition() {
        return YPosition;
    }

    public void setXPosition(float XPosition) {
        this.XPosition = XPosition;
    }

    public void setYPosition(float YPosition) {
        this.YPosition = YPosition;
    }

    public void drawToCanvas(GameView g, Canvas c) {
        return;
    }

    void updatePosition(long fps)
    {
        float[] prevPosition = {getXPosition(), getYPosition()};
        if(isMoving && isFacingRight){
            setXPosition(getXPosition() + (movementSpeed / fps));
        }
        else if(isMoving && !isFacingRight) {
            setXPosition(getXPosition() - (movementSpeed / fps));
        }

//        float[] currentPosition = {getXPosition(), getYPosition()};
//        if(currentPosition[0] != prevPosition[0])
//        {
//            Log.i("ENTITY", "Entity; " + this.name + "\'s X Position has changed");
//        }
//        if(currentPosition[1] != prevPosition[1])
//        {
//            Log.i("ENTITY", "Entity; " + this.name + "\'s Y Position has changed");
//        }
    }

}
