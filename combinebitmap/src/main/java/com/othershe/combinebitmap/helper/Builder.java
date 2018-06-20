package com.othershe.combinebitmap.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.widget.ImageView;

import com.othershe.combinebitmap.layout.DingLayoutManager;
import com.othershe.combinebitmap.layout.ILayoutManager;
import com.othershe.combinebitmap.layout.WechatLayoutManager;

public class Builder {
    public Context context;
    public ImageView imageView;
    public int size; // 最终生成bitmap的尺寸
    public int gap; // 每个小bitmap之间的距离
    public int gapColor; // 间距的颜色
    public int placeholder; // 获取图片失败时的默认图片
    public int count; // 要加载的资源数量
    public int subSize; // 单个bitmap的尺寸

    public ILayoutManager layoutManager; // bitmap的组合样式

    public OnProgressListener progressListener; // 最终的组合bitmap回调接口

    public Bitmap[] bitmaps;
    public int[] resourceIds;
    public String[] urls;


    public Builder(Context context) {
        this.context = context;
    }

    public Builder setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public Builder setSize(int size) {
        this.size = Utils.dp2px(context, size);
        return this;
    }

    public Builder setGap(int gap) {
        this.gap = Utils.dp2px(context, gap);
        return this;
    }

    public Builder setGapColor(@ColorInt int gapColor) {
        this.gapColor = gapColor;
        return this;
    }

    public Builder setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public Builder setLayoutManager(ILayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    public Builder setOnProgressListener(OnProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    public Builder setBitmaps(Bitmap... bitmaps) {
        this.bitmaps = bitmaps;
        this.count = bitmaps.length;
        return this;
    }

    public Builder setUrls(String... urls) {
        this.urls = urls;
        this.count = urls.length;
        return this;
    }

    public Builder setResourceIds(int... resourceIds) {
        this.resourceIds = resourceIds;
        this.count = resourceIds.length;
        return this;
    }

    public void build() {
        subSize = getSubSize(size, gap, layoutManager, count);
        CombineHelper.init().load(this);
    }

    /**
     * 根据最终生成bitmap的尺寸，计算单个bitmap尺寸
     * @param size
     * @param gap
     * @param layoutManager
     * @param count
     * @return
     */
    private int getSubSize(int size, int gap, ILayoutManager layoutManager, int count) {
        int subSize = 0;
        if (layoutManager instanceof DingLayoutManager) {
            subSize = size;
        } else if (layoutManager instanceof WechatLayoutManager) {
            if (count < 2) {
                subSize = size;
            } else if (count < 5) {
                subSize = (size - 3 * gap) / 2;
            } else if (count < 10) {
                subSize = (size - 4 * gap) / 3;
            }
        }
        return subSize;
    }
}
