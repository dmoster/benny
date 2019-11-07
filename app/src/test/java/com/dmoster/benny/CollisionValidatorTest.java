package com.dmoster.benny;

import org.junit.Test;

import static org.junit.Assert.*;


public class CollisionValidatorTest {

  @Test
  public void zeroHealth_killsPlayer() {
    
    PlayableCharacter player1 = new PlayableCharacter(1);
    player1.kill();
    assertFalse(player1.isAlive());

    PlayableCharacter player2 = new PlayableCharacter(3);
    player2.kill();
    assertFalse(player2.isAlive());

    PlayableCharacter player3 = new PlayableCharacter(15);
    player3.kill();
    assertFalse(player3.isAlive());
  }
}