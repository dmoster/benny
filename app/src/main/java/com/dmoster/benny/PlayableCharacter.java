package com.dmoster.benny;

public class PlayableCharacter extends Sprite {

  public PlayableCharacter(int health) {
    super();
    setHealth(health);
    setDX(1.0); //for testing
  }

  @Override
  protected void kill() {
    setHealth(0);
  }
}
