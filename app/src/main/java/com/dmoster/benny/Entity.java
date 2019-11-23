package com.dmoster.benny;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public abstract class Entity {

    String name = "Unnamed Entity";

    int bitmapFrameHeight = 256;
    int bitmapFrameWidth = 256;

    public float fallingMultiplier = 2.5f;

    public boolean isMoving;
    public boolean isFacingRight;

    private float XPosition;
    private float YPosition;

    private float YVelocity;

    RectF whereToDraw;
    Rect frameToDraw;

    int currentFrame = 0;
    int currentAnimationFrameCount = 0;

    float movementSpeed;
    float jumpHeight = 10;

    int groundDetectionOffset = 15;

    public Entity(int xPos, int yPos, int movementSpeed, String name)
    {
        this.name = name;
        setXPosition(xPos);
        setYPosition(yPos);
        whereToDraw = new RectF(getXPosition(), getYPosition(), getXPosition() + bitmapFrameWidth, bitmapFrameHeight + getYPosition());
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

    public float getYVelocity() {
        return YVelocity;
    }

    public void setYVelocity(float YVelocity) {
        this.YVelocity = YVelocity;
    }

    public void addYVelocity(float add) {
        this.YVelocity += add;
    }

    public void drawToCanvas(GameView g, Canvas c) {
        return;
    }

    void updatePosition(long fps)
    {
        if(isMoving && isFacingRight){
            setXPosition(getXPosition() + (movementSpeed / fps));
        }
        else if(isMoving && !isFacingRight) {
            setXPosition(getXPosition() - (movementSpeed / fps));
        }
        if(getYVelocity() != 0)
        {
            Log.i("ENTITY", "Y Velocity Before change" + getYVelocity());
            Log.i("ENTITY", "Changing Y Position" + getYPosition());
            setYPosition(getYPosition() + getYVelocity());
        }

    }

    void jump(float deltaTime)
    {
        if(getYPosition() < 400 + groundDetectionOffset && getYPosition() > 400 - groundDetectionOffset)
        {
            Log.i("ENTITY", "Applying jump velocity");
            addYVelocity(-jumpHeight);
        }

    }

    void collide(Entity hitTarget)
    {

    }


}
