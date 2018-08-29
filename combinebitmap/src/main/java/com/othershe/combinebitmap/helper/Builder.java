package com.othershe.combinebitmap.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.othershe.combinebitmap.R;
import com.othershe.combinebitmap.layout.DingLayoutManager;
import com.othershe.combinebitmap.layout.ILayoutManager;
import com.othershe.combinebitmap.layout.WechatLayoutManager;
import com.othershe.combinebitmap.listener.OnProgressListener;
import com.othershe.combinebitmap.listener.OnSubItemClickListener;
import com.othershe.combinebitmap.region.DingRegionManager;
import com.othershe.combinebitmap.region.IRegionManager;
import com.othershe.combinebitmap.region.WechatRegionManager;

public class Builder {
    public Context context;
    public ImageView imageView;
    public int size; // 最终生成bitmap的尺寸
    public int gap; // 每个小bitmap之间的距离
    public int gapColor; // 间距的颜色
    public int placeholder  = R.drawable.defalut_placeholder; // 获取图片失败时的默认图片
    public int count; // 要加载的资源数量
    public int subSize; // 单个bitmap的尺寸

    public ILayoutManager layoutManager; // bitmap的组合样式

    public Region[] regions;
    public OnSubItemClickListener subItemClickListener; // 单个bitmap点击事件回调

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

    public Builder setOnSubItemClickListener(OnSubItemClickListener subItemClickListener) {
        this.subItemClickListener = subItemClickListener;
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
        initRegions();
        CombineHelper.init().load(this);
    }

    /**
     * 根据最终生成bitmap的尺寸，计算单个bitmap尺寸
     *
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
        } else {
            throw new IllegalArgumentException("Must use DingLayoutManager or WechatRegionManager!");
        }
        return subSize;
    }

    /**
     * 初始化RegionManager
     */
    private void initRegions() {
        if (subItemClickListener == null || imageView == null) {
            return;
        }

        IRegionManager regionManager;

        if (layoutManager instanceof DingLayoutManager) {
            regionManager = new DingRegionManager();
        } else if (layoutManager instanceof WechatLayoutManager) {
            regionManager = new WechatRegionManager();
        } else {
            throw new IllegalArgumentException("Must use DingLayoutManager or WechatRegionManager!");
        }

        regions = regionManager.calculateRegion(size, subSize, gap, count);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            int initIndex = -1;
            int currentIndex = -1;
            Point point = new Point();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                point.set((int) event.getX(), (int) event.getY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initIndex = getRegionIndex(point.x, point.y);
                        currentIndex = initIndex;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentIndex = getRegionIndex(point.x, point.y);
                        break;
                    case MotionEvent.ACTION_UP:
                        currentIndex = getRegionIndex(point.x, point.y);
                        if (currentIndex != -1 && currentIndex == initIndex) {
                            subItemClickListener.onSubItemClick(currentIndex);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        initIndex = currentIndex = -1;
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 根据触摸点计算对应的Region索引
     *
     * @param x
     * @param y
     * @return
     */
    private int getRegionIndex(int x, int y) {
        for (int i = 0; i < regions.length; i++) {
            if (regions[i].contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

}
