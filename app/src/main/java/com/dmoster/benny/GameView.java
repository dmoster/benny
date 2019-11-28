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
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

// Notice we implement runnable so we have
// A thread and can override the run method.
class GameView extends SurfaceView implements Runnable {

  private static final String TAG = "GameView";

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

  // Camera and map
  private TileMap map;

  private int[][][] tileMapData = {
    {
      {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
      {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,2,2,2},
      {7,7,7,7,7,7,7,7,7,7,7,7,7,2,2,7,7,7},
      {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
      {2,2,2,2,2,7,7,7,2,2,2,2,2,2,2,2,2,2},
      {7,7,7,7,7,7,7,7,7,2,2,2,2,7,7,7,7,7},
      {7,7,7,7,7,7,7,7,7,7,2,2,2,7,7,7,7,7},
      {7,7,7,7,7,2,2,7,7,7,7,7,7,7,7,7,7,7},
      {7,7,7,7,7,7,7,7,7,2,2,2,2,2,7,7,7,7}
    }
  };

  //Get display info
  private HeadsUpDisplay hud;

  protected DisplayMetrics displayMetrics;
  protected int height;
  protected int width;

  private float gravity = 0.25f;

  ArrayList<Entity> entities = new ArrayList<Entity>();

  // Create Player character object
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

    // Initialize map
    map = new TileMap(tileMapData[0]);

    // Initialize HUD
    hud = new HeadsUpDisplay(3, 0, 0, 12.47);

    // Add entities to list
    entities.add(player);

    Random random = new Random();
    for (int i = 0; i < 6; ++i) {
      switch (random.nextInt(5)) {
        case 0:
          entities.add(new Item(this.getContext(), width / 6, height / 6, 0, R.drawable.purple_lolly, "Purple Lolly"));
          break;
        case 1:
          entities.add(new Item(this.getContext(), width / 2, height / 2, 0, R.drawable.pink_lolly, "Pink Lolly"));
          break;

        case 2:
          entities.add(new Item(this.getContext(), width / 3, height / 3, 0, R.drawable.blue_lolly, "Blue Lolly"));
          break;
        case 3:
          entities.add(new Item(this.getContext(), width / 4, height / 4, 0, R.drawable.green_lolly, "Green Lolly"));
          break;
        case 4:
          entities.add(new Item(this.getContext(), width / 5, height / 5, 0, R.drawable.orange_lolly, "Orange Lolly"));
          break;
      }
    }

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
    //the next frame on the sprite sheet
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
    applyPhysics();
    player.updatePosition(fps);
    handleCollisions();

  }

  private void handleCollisions() {
    //Iterate through each entity, and see if they hit something else.
    for(int i = 0; i < entities.size() - 1; i++)
    {
      for(int a = i + 1; a < entities.size(); a++)
      {
        //Very helpful function! If this is true, we got a hit!
        if(RectF.intersects(entities.get(i).whereToDraw, entities.get(a).whereToDraw))
        {
          //Lets have each entity deal with collisions on their own.
          entities.get(i).collide(entities.get(a));
          entities.get(a).collide(entities.get(i));
        }
      }
    }
  }

  private void applyPhysics() {
    for(Entity e : entities)
    {
        e.addYVelocity(gravity);

      //We set this to 400 cause its our current ground... we should be checking for the height we collide with the ground.
      if(e.getYPosition() > 400) {
        e.setYPosition(400);
        e.setYVelocity(0);
      }

    }
  }

  // Draw the newly updated scene
  public void draw() {

    // Make sure our drawing surface is valid or we crash
    if (ourHolder.getSurface().isValid()) {
      // Lock the canvas ready to draw
      canvas = ourHolder.lockCanvas();

      // New drawing code goes here
      map.draw(canvas, width, height);

      // Choose the brush color for drawing
      paint.setColor(Color.argb(255, 249, 129, 0));


      for (Entity entity: entities) {
        entity.drawToCanvas(this, canvas);
      }

      //Draw the hud on the screen
      hud.draw(paint, canvas, width);


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
        } else if (touchXPosition > width - 300 && touchXPosition < width &&
                touchYPosition > 700 && touchYPosition < 1000){
          Log.i("GAMEVIEW", "Jump button pressed");
          player.jump(lastFrameChangeTime);
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