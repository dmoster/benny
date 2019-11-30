package com.dmoster.benny;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HeadsUpDisplay {

  private static HeadsUpDisplay singleton = null;

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

  public static HeadsUpDisplay getInstance()
  {
    if(singleton == null)
      singleton = new HeadsUpDisplay(3, 0, 0, 12.47);

    return singleton;
  }

  public int getLives() { return lives; }
  public int getTshirts() { return tshirts; }
  public int getLollies() { return lollies; }
  public double getMoney() { return money; }

  public void setLives(int lives) { this.lives = lives; }
  public void setTshirts(int tshirts) { this.tshirts = tshirts; }
  public void setLollies(int lollies) { this.lollies = lollies; }
  public void setMoney(double money) { this.money = money; }

  public void addLives(int lives) { this.lives += lives; }
  public void addTshirts(int tshirts) { this.tshirts += tshirts; }
  public void addLollies(int lollies) { this.lollies += lollies; }
  public void addMoney(double money) { this.money += money; }

  public void draw(Paint paint, Canvas canvas, final int width) {
    // Choose the brush color for drawing
    paint.setColor(Color.argb(180, 106, 90, 205));

    // Make the text a bit bigger
    paint.setTextSize(60);

    // Draw counters
    canvas.drawText("Lives: " + lives, 70, 70, paint);
    canvas.drawText("Shirts: " + tshirts, 70 + width - (4 * width / 5), 70, paint);
    canvas.drawText("Lollies: " + lollies, 70 + width - (3 * width / 5), 70, paint);
    canvas.drawText("Money: $" + money, 70 + width - (2 * width / 5), 70, paint);

    // Draw pause button
    canvas.drawRect(width - 65, 20, width - 45, 80, paint);
    canvas.drawRect(width - 25, 20, width - 5, 80, paint);

    //Temp jump button
    canvas.drawRect(width - 300, 1000, width - 5, 700, paint);
  }
}
