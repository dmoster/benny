package com.dmoster.benny;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  // gameView will be the view of the game
  // It will also hold the logic of the game
  // and respond to screen touches as well
  static GameView gameView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initialize gameView and set it as the view
    gameView = new GameView(this);
    setContentView(gameView);

//    // Go to start/pause menu
//    showMenu();
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      hideSystemUI();
    }
  }

  private void hideSystemUI() {
    // Enables regular immersive mode.
    // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
    // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_IMMERSIVE
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }


  // This method executes when the player starts the game
  @Override
  protected void onResume() {
    super.onResume();

    // Tell the gameView resume method to execute
    gameView.resume();
  }

  public void showMenu() {

    // Create new Intent which calls PauseMenu
    Intent pause = new Intent(this, PauseMenu.class);
    startActivity(pause);
  }

  // This method executes when the player quits the game
  @Override
  protected void onPause() {
    super.onPause();

    // Tell the gameView pause method to execute
    gameView.pause();

    // Show pause menu
    showMenu();
  }

}