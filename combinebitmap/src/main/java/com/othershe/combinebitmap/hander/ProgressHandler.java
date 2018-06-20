package com.othershe.combinebitmap.hander;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class ProgressHandler extends Handler {
    private int i = 0;

    private Bitmap[] bitmaps;

    private Bitmap defaultBitmap;
    private HandlerListener listener;

    public ProgressHandler(Bitmap defaultBitmap, int count, HandlerListener listener) {
        this.defaultBitmap = defaultBitmap;
        this.bitmaps = new Bitmap[count];
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1://成功
                bitmaps[msg.arg1] = (Bitmap) msg.obj;
                break;
            case 2://失败
                bitmaps[msg.arg1] = defaultBitmap;
                break;
        }
        i++;
        if (i == bitmaps.length && listener != null) {
            listener.onComplete(bitmaps);
        }
    }
}
