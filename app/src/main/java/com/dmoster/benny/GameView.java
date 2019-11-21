package com.dmoster.benny;

// GameView class will go here

// Here is our implementation of GameView
// It is an inner class.
// Note how the final closing curly brace }
// is inside SimpleGameEngine

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
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

  //Get display info
  private HeadsUpDisplay hud;

  protected DisplayMetrics displayMetrics;
  protected int height;
  protected int width;

  //Create Player character object
  PlayerCharacter player = new PlayerCharacter(this.getContext());

  // What time was it when we last changed frames
  private long lastFrameChangeTime = 0;

  // How long should each frame last
  private int frameLengthInMilliseconds = 150;

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

    // Initialize display info
    displayMetrics = new DisplayMetrics();
    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    height = displayMetrics.heightPixels;
    width = displayMetrics.widthPixels;

    //Initilize HUD
    hud = new HeadsUpDisplay(3, 0, 0, 12.47);

    // Set our boolean to true - game on!
    playing = true;

  }

  public void getCurrentFrame(Entity e){
    //Get delta time, and find the frame we need to show.
    long time  = System.currentTimeMillis();
      if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
        lastFrameChangeTime = time;
        e.currentFrame ++;
        if (e.currentFrame >= e.currentAnimationFrameCount) {

          e.currentFrame = 0;
        }
    }
    //update the left and right values of the source of
    //the next frame on the spritesheet
    e.frameToDraw.left = e.currentFrame * e.bitmapFrameWidth;
    e.frameToDraw.right = e.frameToDraw.left + e.bitmapFrameWidth;

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

    //Update players movement
    //Log.i("GameView", "Updating Player Position...");
    player.updatePosition(fps);

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

      //Draw the hud on the screen
      hud.draw(paint, canvas, width);

      player.drawToCanvas(this, canvas);
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
    //Get location of touch
    float touchXPosition = motionEvent.getX();
    float touchYPosition = motionEvent.getY();

    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

      // Player has touched the screen
      case MotionEvent.ACTION_DOWN:
        //Player touched right location
        if (touchXPosition > width - 70 && touchXPosition < width &&
                touchYPosition > 20 && touchYPosition < 80) {
          //Show the menu
          ((MainActivity) getContext()).showMenu();
        } else {
        // Make Player move in direction touched
        player.isMoving = true;
        if(touchXPosition < player.getXPosition())
          player.isFacingRight = false;
        else
          player.isFacingRight = true;
        }

        break;

      // Player has removed finger from screen
      case MotionEvent.ACTION_UP:

        // Set isMoving so character does not move
        player.isMoving = false;

        break;
    }
    return true;
  }

}
// This is the end of our GameView inner class