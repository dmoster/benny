package com.dmoster.benny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import static com.dmoster.benny.MainActivity.gameView;
import static java.security.AccessController.getContext;

public class PlayerCharacter extends Entity {

    BitmapFunctions bitFunc = new BitmapFunctions();

    MediaPlayer mediaPlayer;

    private Bitmap standBitmap;
    private Bitmap standBitmapMirrored;
    private Bitmap walkBitmap;
    private Bitmap walkBitmapMirrored;
    private Bitmap bulldozeBitmap;
    private Bitmap bulldozeBitmapMirrored;

    Map<String, Integer> frameCount = new HashMap<String,Integer>();

    public PlayerCharacter() {
        super(100, 0, 300, "Default Player");
        frameCount.put("Stand", 5);
        frameCount.put("Walk", 2);
        frameCount.put("Bulldoze", 2);
        Log.e("PLAYER CHARACTER CLASS:", "CALLED DEFAULT CONSTRUCTOR, NO BITMAPS CREATED!");
    }

    public PlayerCharacter(Context c)
    {
        super(100, 300, 300, "Benny");
        isFacingRight = true;
        AddHashMapValues();
        Log.i("PLAYER CHARACTER", "HashMapValues Loaded");
        standBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_stand, frameCount.get("Stand"));
        standBitmapMirrored = bitFunc.FlipBitmap(standBitmap);
        walkBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_walk, frameCount.get("Walk"));
        walkBitmapMirrored = bitFunc.FlipBitmap(walkBitmap);
        bulldozeBitmap = bitFunc.LoadBitmap(this, c, R.drawable.benny_bulldoze, frameCount.get("Bulldoze"));
        bulldozeBitmapMirrored = bitFunc.FlipBitmap(bulldozeBitmap);
        Log.i("PLAYER CHARACTER", "Hashmaps and Mirrored Hashmaps created");
    }


    public void AddHashMapValues()
    {
        frameCount.put("Stand", 5);
        frameCount.put("Walk", 2);
        frameCount.put("Bulldoze", 2);
    }

    @Override
    public void drawToCanvas(GameView g, Canvas c) {
        whereToDraw.set((int) getXPosition(),
                getYPosition(),
                (int) getXPosition() + bitmapFrameWidth,
                getYPosition() + bitmapFrameHeight);

        if (isFacingRight && isMoving) {
            currentAnimationFrameCount = frameCount.get("Walk");

            g.getCurrentFrame(this);

            c.drawBitmap(walkBitmap,
                    frameToDraw,
                    whereToDraw, g.paint);
        } else if (!isFacingRight && isMoving) {
            currentAnimationFrameCount = frameCount.get("Walk");

            g.getCurrentFrame(this);

            c.drawBitmap(walkBitmapMirrored,
                    frameToDraw,
                    whereToDraw, g.paint);
        }

        else if(!isMoving && isFacingRight)
        {
            currentAnimationFrameCount = frameCount.get("Stand");

            g.getCurrentFrame(this);

            c.drawBitmap(standBitmap,
                    frameToDraw,
                    whereToDraw, g.paint);
        } else if(!isMoving && !isFacingRight) {
            currentAnimationFrameCount = frameCount.get("Stand");

            g.getCurrentFrame(this);

            c.drawBitmap(standBitmapMirrored,
                    frameToDraw,
                    whereToDraw, g.paint);
        }
    }

    @Override
    public void collideEntity(Entity hitTarget, Context context) {
        Log.i("PLAYER CHARACTER", "Hit an entity");
        if (hitTarget.name == "Purple Lolly") {
            gameView.hud.addLollies(1);
            gameView.hud.subtractMoney(0.15);
            playPickupSound(context);
        }
        else if (hitTarget.name == "Pink Lolly") {
            gameView.hud.addLollies(2);
            gameView.hud.subtractMoney(0.25);
            playPickupSound(context);
        }
        else if (hitTarget.name == "Red Lolly") {
            gameView.hud.addLollies(3);
            gameView.hud.subtractMoney(0.35);
            playPickupSound(context);
        }
        else if (hitTarget.name == "Green Lolly") {
            gameView.hud.addLollies(4);
            gameView.hud.subtractMoney(0.45);
            playPickupSound(context);
        }
        else if (hitTarget.name == "Blue Lolly") {
            gameView.hud.addLollies(5);
            gameView.hud.subtractMoney(0.55);
            playPickupSound(context);
        }
        else if (hitTarget.name == "Orange Lolly") {
            gameView.hud.addLollies(6);
            gameView.hud.subtractMoney(0.65);
            playPickupSound(context);
        } else if (hitTarget.name == "Old Lady")
        {
            alive = false;
            playHitSound(context);
        } else if (hitTarget.name == "Hipster")
        {
            alive = false;
            playHitSound(context);
        }
    }

    @Override
    void jump(Context c) {
        super.jump(c);
        playJumpSound(c);
    }


    public void kill() {
        gameView.hud.subtractLives(1);
        gameView.player.setXPosition(0);
        gameView.player.setYPosition(0);

        if (gameView.hud.getLives() == 0) {
            gameView.map.setCurrentMap(0);
            gameView.hud = new HeadsUpDisplay(3, 0, 0, 12.47);
        }
    }

    public void playPickupSound(Context c)
    {
        mediaPlayer = MediaPlayer.create(c, R.raw.pickup);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp = null;
            }
        });
        mediaPlayer.start();
    }

    public void playHitSound(Context c)
    {
        mediaPlayer = MediaPlayer.create(c, R.raw.hit);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp = null;
            }
        });
        mediaPlayer.start();
    }

    public void playJumpSound(Context c)
    {
        mediaPlayer = MediaPlayer.create(c, R.raw.jump);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp = null;
            }
        });
        mediaPlayer.start();
    }

}
