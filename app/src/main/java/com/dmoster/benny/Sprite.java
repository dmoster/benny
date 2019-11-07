package com.dmoster.benny;

public abstract class Sprite {

  private double dX;
  private double dY;
  private int health;

  void Sprite() {
    setDX(0.0);
    setDY(0.0);;
  }

  public double getDX() { return dX; }
  protected double getDY() { return dY; }
  protected int getHealth() { return health; }
  protected boolean isAlive() { return health > 0; }

  protected void setDX(double dX) { this.dX = dX; }
  protected void setDY(double dY) { this.dY = dY; }
  protected void setHealth(int health) { this.health = health; }
  abstract protected void kill();
}
