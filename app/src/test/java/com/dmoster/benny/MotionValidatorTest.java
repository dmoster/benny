package com.dmoster.benny;

import org.junit.Test;

import static org.junit.Assert.*;


public class MotionValidatorTest {

  @Test
  public void rightArrow_movesRight() {
    boolean rightArrow = true;
    PlayableCharacter benny = new PlayableCharacter(3);

    if (rightArrow) {
      assertTrue(benny.getDX() > 0.0);
    }
  }
}