package com.othershe.combinebitmap.layout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class DingLayoutManager implements ILayoutManager {
    @Override
    public Bitmap combineBitmap(int size, int subSize, int gap, int gapColor, Bitmap[] bitmaps) {
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        if (gapColor == 0) {
            gapColor = Color.WHITE;
        }
        canvas.drawColor(gapColor);

        int count = bitmaps.length;
        Bitmap subBitmap;

        int[][] dxy = {{0, 0}, {1, 0}, {1, 1}, {0, 1}};

        for (int i = 0; i < count; i++) {
            if (bitmaps[i] == null) {
                continue;
            }
            subBitmap = Bitmap.createScaledBitmap(bitmaps[i], size, size, true);
            if (count == 2 || (count == 3 && i == 0)) {
                subBitmap = Bitmap.createBitmap(subBitmap, (size + gap) / 4, 0, (size - gap) / 2, size);
            } else if ((count == 3 && (i == 1 || i == 2)) || count == 4) {
                subBitmap = Bitmap.createBitmap(subBitmap, (size + gap) / 4, (size + gap) / 4, (size - gap) / 2, (size - gap) / 2);
            }

            int dx = dxy[i][0];
            int dy = dxy[i][1];

            canvas.drawBitmap(subBitmap, dx * (size + gap) / 2.0f, dy * (size + gap) / 2.0f, null);
        }
        return result;
    }
}
