package com.dmoster.benny;

import org.junit.Test;

import static org.junit.Assert.*;


public class HUDValidatorTest {

  @Test
  public void gettingLolly_incrementsLolliesAndReducesMoney() {
    HeadsUpDisplay hud = new HeadsUpDisplay(1, 0, 2, 1.55);

    int initLollies = hud.getLollies();
    double initMoney = hud.getMoney();
    boolean gotLolly = true;

    if (gotLolly) {

      hud.setLollies(hud.getLollies() + 1);
      hud.setMoney(hud.getMoney() - 0.65);

      assertEquals(1, hud.getLollies() - initLollies);
      assertEquals(0.65, initMoney - hud.getMoney(), 0.001);
    }
  }
}