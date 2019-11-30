package com.dmoster.benny;

/**
 * An abstract Class responsible for every character considered an "entity". An entity being a character that can move around, and interact with other entities.
 *
 * This class is used to determine the bitmap size, the position and rotation of a character, and collisions with other entities.
 * drawn on-screen.
 *
 * @author dmoster
 * @version 2019.1128
 * @since 1.0
 */

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public abstract class Entity {
    private static final String TAG = "ENTITY";

    String name = "Unnamed Entity";

    int bitmapFrameHeight = 128;
    int bitmapFrameWidth = 128;

    public boolean isMoving;
    public boolean isFacingRight;
    public boolean grounded = true;

    private float XPosition;
    private float YPosition;

    private float YVelocity;

    RectF whereToDraw;
    Rect frameToDraw;

    int currentFrame = 0;
    int currentAnimationFrameCount = 0;

    float movementSpeed;
    float jumpHeight = 15;

    /**
     * Constructor that creates a new entity, this should never be called on its own.
     * @param xPos the starting x position of the entity stored in an int
     * @param yPos the starting y position of the entity stored in an int
     * @param movementSpeed the speed that which a unit will move across the screen. 0 means the unit is stationary.
     * @param name the name of the entity, just so its easier to find and log info.
     */

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

    /**
     * updatePosition Method for entity
     *
     * @param fps The current fps is passed in to ensure nothing wonky happens at extremely low or high fps.
     */

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
            setYPosition(getYPosition() + getYVelocity());
        }

    }

    /**
     * jump method for Entity, adds upward velocity to the entity.
     *
     */

    void jump()
    {
        if(grounded)
        {
            grounded = false;
            addYVelocity(-jumpHeight);
        }

    }

    /**
     * collideEntity method for Entity. We expect this to be overriden for extra logic specific to entities.
     *
     * @param hitTarget a reference to the entity that this entity collided with.
     */

    public void collideEntity(Entity hitTarget)
    {
        return;
    }

    /**
     * collideMap method for Entity. This doesnt need a parameter because when we touch ground, the logic will always be the same
     *
     */

    public void collideMap()
    {
        grounded = true;
    }



}
