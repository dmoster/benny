package com.dmoster.benny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapFunctions {
    //To use this function you need to pass in the entity data, the context (like "this"), A file (R.drawable.filename)
    // and the expected number of frames in the animation
    Bitmap LoadBitmap(Entity entity, Context context, int id, int expectedFrames)
    {
        Bitmap tempBitMap = BitmapFactory.decodeResource(context.getResources(), id);
        tempBitMap = Bitmap.createScaledBitmap(tempBitMap,
                entity.bitmapFrameWidth * expectedFrames, entity.bitmapFrameHeight, false);
        return tempBitMap;
    }

    Bitmap FlipBitmap(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m , true);
    }

}
