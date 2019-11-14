package com.dmoster.benny;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//  public static PlayableCharacter benny;
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_main);
//
//    benny = new PlayableCharacter(3);
//  }
//}

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  // gameView will be the view of the game
  // It will also hold the logic of the game
  // and respond to screen touches as well
  GameView gameView;
//here is my comment
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initialize gameView and set it as the view
    gameView = new GameView(this);
    setContentView(gameView);

  }

  // This method executes when the player starts the game
  @Override
  protected void onResume() {
    super.onResume();

    // Tell the gameView resume method to execute
    gameView.resume();
  }

  // This method executes when the player quits the game
  @Override
  protected void onPause() {
    super.onPause();

    // Tell the gameView pause method to execute
    gameView.pause();
  }

}