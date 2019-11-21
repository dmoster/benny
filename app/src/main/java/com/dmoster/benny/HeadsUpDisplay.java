package com.dmoster.benny;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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

  public void draw(Paint paint, Canvas canvas, final int width) {
    // Choose the brush color for drawing
    paint.setColor(Color.argb(255, 249, 129, 0));

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


    canvas.drawRect(100, 656, 228, 784, paint);
    canvas.drawRect(228, 656, 356, 784, paint);
  }
}
