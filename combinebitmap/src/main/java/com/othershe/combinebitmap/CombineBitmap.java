package com.othershe.combinebitmap;

import android.content.Context;

import com.othershe.combinebitmap.helper.Builder;

public class CombineBitmap {
    public static Builder init(Context context) {
        return new Builder(context);
    }
}
