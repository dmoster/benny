package com.dmoster.benny;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public abstract class Entity {

    int bitmapFrameHeight = 512;
    int bitmapFrameWidth = 512;

    public boolean isMoving;
    public boolean isFacingRight;

    float XPosition;
    float YPosition;

    RectF whereToDraw;
    Rect frameToDraw;

    int currentFrame = 0;
    int currentAnimationFrameCount = 0;

    float movementSpeed;

    public Entity(int xPos, int yPos, int movementSpeed)
    {
        setXPosition(xPos);
        setYPosition(yPos);
        whereToDraw = new RectF(getXPosition(), 0, getXPosition() + bitmapFrameWidth, bitmapFrameHeight);
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

    void updatePosition(long fps)
    {
        if(isMoving && isFacingRight){
            setXPosition(getXPosition() + (movementSpeed / fps));
        }
        else if(isMoving && !isFacingRight) {
            setXPosition(getXPosition() - (movementSpeed / fps));
        }
    }

}
