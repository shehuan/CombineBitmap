package com.othershe.combinebitmap.listener;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public interface OnBitmapLoaded {
    void onComplete(Bitmap bitmap);
    void onFailed(@Nullable Bitmap bitmap);
}
