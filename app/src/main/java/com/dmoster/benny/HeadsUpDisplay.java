package com.dmoster.benny;

public class HeadsUpDisplay {
  private int lives;
  private int tshirts;
  private int lollies;
  private double money;

  public HeadsUpDisplay(int lives, int tshirts, int lollies, double money) {
    this.lives = lives;
    this.tshirts = tshirts;
    this.lollies = lollies;
    this.money = money;
  }

  public int getLives() { return lives; }
  public int getTshirts() { return tshirts; }
  public int getLollies() { return lollies; }
  public double getMoney() { return money; }

  public void setLives(int lives) { this.lives = lives; }
  public void setTshirts(int tshirts) { this.tshirts = tshirts; }
  public void setLollies(int lollies) { this.lollies = lollies; }
  public void setMoney(double money) { this.money = money; }
}
