package com.othershe.combinebitmap.layout;

import android.graphics.Bitmap;

public interface ILayoutManager {
    Bitmap combineBitmap(int size, int subSize, int gap, int gapColor, Bitmap[] bitmaps);
}
