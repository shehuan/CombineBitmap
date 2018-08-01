package com.othershe.combinebitmap.helper;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.othershe.combinebitmap.listener.OnHandlerListener;

import java.util.ArrayList;
import java.util.List;

public class ProgressHandler extends Handler {
    private int i = 0;
//    temp arraylist for bitmap
    private List<Bitmap> tempbitmap= new ArrayList();
    private Bitmap[] bitmaps;
//default number
    private int defaultnum=0;
    private Bitmap defaultBitmap;
    private OnHandlerListener listener;
    private int count;
    public ProgressHandler(Bitmap defaultBitmap, int count, OnHandlerListener listener) {
        this.defaultBitmap = defaultBitmap;
        this.bitmaps = new Bitmap[count];
        this.listener = listener;
        this.count=count;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1://成功
                tempbitmap.add((Bitmap)msg.obj);
                break;
            case 2://失败
                defaultnum++;
                break;
        }
        ++i;
//        If the icon is empty, order it back to the exist icons
        if(count==(this.tempbitmap.size()+defaultnum))
        {
            for(int i=0;i<defaultnum;i++)
            {
                this.tempbitmap.add(defaultBitmap);
            }
        }
        if (count == this.tempbitmap.size() && this.listener != null) {
            listener.onComplete(tempbitmap.toArray(new Bitmap[count]));
        }
    }
}
