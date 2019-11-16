package com.dmoster.benny;

// GameView class will go here

// Here is our implementation of GameView
// It is an inner class.
// Note how the final closing curly brace }
// is inside SimpleGameEngine

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// Notice we implement runnable so we have
// A thread and can override the run method.
class GameView extends SurfaceView implements Runnable {

  // This is our thread
  Thread gameThread = null;

  // This is new. We need a SurfaceHolder
  // When we use Paint and Canvas in a thread
  // We will see it in action in the draw method soon.
  SurfaceHolder ourHolder;

  // A boolean which we will set and unset
  // when the game is running- or not.
  volatile boolean playing;

  // A Canvas and a Paint object
  Canvas canvas;
  Paint paint;

  // This variable tracks the game frame rate
  long fps;

  // This is used to help calculate the fps
  private long timeThisFrame;

  // Declare an object of type Bitmap
  Bitmap bitmapBob;
  Bitmap bitmapBobMirrored;

  // Bob starts off not moving
  boolean isMoving = false;

  //Bob starts off facing right
  boolean isFacingRight;

  // He can walk at 150 pixels per second
  float walkSpeedPerSecond = 150;

  // He starts 10 pixels from the left
  float bobXPosition = 10;

  // New variables for the sprite sheet animation

  // These next two values can be anything you like
// As long as the ratio doesn't distort the sprite too much
  private int frameWidth = 512;
  private int frameHeight = 512;

  // How many frames are there on the sprite sheet?
  private int frameCount = 2;

  // Start at the first frame - where else?
  private int currentFrame = 0;

  // What time was it when we last changed frames
  private long lastFrameChangeTime = 0;

  // How long should each frame last
  private int frameLengthInMilliseconds = 200;

  // A rectangle to define an area of the
// sprite sheet that represents 1 frame
  private Rect frameToDraw = new Rect(
      0,
      0,
      frameWidth,
      frameHeight);

  // A rect that defines an area of the screen
// on which to draw
  RectF whereToDraw = new RectF(
      bobXPosition,0,
      bobXPosition + frameWidth,
      frameHeight);


  // When the we initialize (call new()) on gameView
  // This special constructor method runs
  public GameView(Context context) {
    // The next line of code asks the
    // SurfaceView class to set up our object.
    // How kind.
    super(context);

    // Initialize ourHolder and paint objects
    ourHolder = getHolder();
    paint = new Paint();

    // Set scaling options for player character
//    BitmapFactory.Options options = new BitmapFactory.Options();
//    options.inDensity = 480;

    // Load Bob from his .png file
    bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.benny_walk);
//    bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.benny_walk, options);
    //Load Bob from his .png file. This will be flipped soon.
    bitmapBobMirrored = BitmapFactory.decodeResource(this.getResources(), R.drawable.benny_walk);

    // Scale the bitmap to the correct size
// We need to do this because Android automatically
// scales bitmaps based on screen density
    bitmapBob = Bitmap.createScaledBitmap(bitmapBob,
        frameWidth * frameCount,
        frameHeight,
        false);

    // Same as above, but with the other bitmap
    bitmapBobMirrored = Bitmap.createScaledBitmap(bitmapBobMirrored,
            frameWidth * frameCount,
            frameHeight,
            false);

    Matrix m = new Matrix();
    m.preScale(-1.0f, 1.0f);
    bitmapBobMirrored = Bitmap.createBitmap(bitmapBobMirrored, 0, 0, bitmapBobMirrored.getWidth(), bitmapBobMirrored.getHeight(), m, true);

    // Set our boolean to true - game on!
    playing = true;

  }

  public void getCurrentFrame(){

    long time  = System.currentTimeMillis();
    if(isMoving) {// Only animate if bob is moving
      if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
        lastFrameChangeTime = time;
        currentFrame ++;
        if (currentFrame >= frameCount) {

          currentFrame = 0;
        }
      }
    }
    //update the left and right values of the source of
    //the next frame on the spritesheet
    frameToDraw.left = currentFrame * frameWidth;
    frameToDraw.right = frameToDraw.left + frameWidth;

  }

  @Override
  public void run() {
    while (playing) {

      // Capture the current time in milliseconds in startFrameTime
      long startFrameTime = System.currentTimeMillis();

      // Update the frame
      update();

      // Draw the frame
      draw();

      // Calculate the fps this frame
      // We can then use the result to
      // time animations and more.
      timeThisFrame = System.currentTimeMillis() - startFrameTime;
      if (timeThisFrame > 0) {
        fps = 1000 / timeThisFrame;
      }

    }

  }

  // Everything that needs to be updated goes in here
  // In later projects we will have dozens (arrays) of objects.
  // We will also do other things like collision detection.
  public void update() {

    // If bob is moving (the player is touching the screen)
    // then move him to the right based on his target speed and the current fps.
    if(isMoving && isFacingRight){
      bobXPosition = bobXPosition + (walkSpeedPerSecond / fps);
    }
    else if(isMoving && !isFacingRight) {
      bobXPosition = bobXPosition - (walkSpeedPerSecond / fps);
    }

  }

  // Draw the newly updated scene
  public void draw() {

    // Make sure our drawing surface is valid or we crash
    if (ourHolder.getSurface().isValid()) {
      // Lock the canvas ready to draw
      canvas = ourHolder.lockCanvas();

      // Draw the background color
      canvas.drawColor(Color.argb(255, 26, 128, 182));

      // Choose the brush color for drawing
      paint.setColor(Color.argb(255, 249, 129, 0));

      // Make the text a bit bigger
      paint.setTextSize(45);

      // Display the current fps on the screen
      canvas.drawText("FPS:" + fps, 20, 40, paint);

//      // Draw a box
//      canvas.drawRect(200, 580, 600, 650, paint);


      if(isFacingRight) {
        whereToDraw.set((int) bobXPosition,
                400,
                (int) bobXPosition + frameWidth,
                400 + frameHeight);

        getCurrentFrame();

        canvas.drawBitmap(bitmapBob,
                frameToDraw,
                whereToDraw, paint);
      } else if (!isFacingRight) {
        whereToDraw.set((int) bobXPosition,
                400,
                (int) bobXPosition + frameWidth,
                400 + frameHeight);

        getCurrentFrame();

        canvas.drawBitmap(bitmapBobMirrored,
                frameToDraw,
                whereToDraw, paint);

      }
// New drawing code goes here


      // Draw everything to the screen
      ourHolder.unlockCanvasAndPost(canvas);
    }

  }

  // If SimpleGameEngine Activity is paused/stopped
  // shutdown our thread.
  public void pause() {
    playing = false;
    try {
      gameThread.join();

      // Create new Intent which calls PauseMenu


    } catch (InterruptedException e) {
      Log.e("Error:", "joining thread");
    }

  }

  // If SimpleGameEngine Activity is started then
  // start our thread.
  public void resume() {
    playing = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  // The SurfaceView class implements onTouchListener
  // So we can override this method and detect screen touches.
  @Override
  public boolean onTouchEvent(MotionEvent motionEvent) {

    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

      // Player has touched the screen
      case MotionEvent.ACTION_DOWN:

        // Set isMoving so Bob is moved in the update method
        isMoving = true;
        float touchXPosition = motionEvent.getX();
        if(touchXPosition < bobXPosition)
          isFacingRight = false;
        else
          isFacingRight = true;

        System.out.println(isFacingRight);
        break;

      // Player has removed finger from screen
      case MotionEvent.ACTION_UP:

        // Set isMoving so Bob does not move
        isMoving = false;

        break;
    }
    return true;
  }

}
// This is the end of our GameView inner class