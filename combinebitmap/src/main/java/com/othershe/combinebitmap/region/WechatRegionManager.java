package com.othershe.combinebitmap.region;

import android.graphics.Path;
import android.graphics.Region;

public class WechatRegionManager implements IRegionManager {

    @Override
    public Region[] calculateRegion(int size, int subSize, int gap, int count) {
        Region[] regions = new Region[count];
        Region globalRegion = new Region(0, 0, size, size);

        for (int i = 0; i < count; i++) {

            float x = 0;
            float y = 0;

            if (count == 2) {
                x = gap + i * (subSize + gap);
                y = (size - subSize) / 2.0f;
            } else if (count == 3) {
                if (i == 0) {
                    x = (size - subSize) / 2.0f;
                    y = gap;
                } else {
                    x = gap + (i - 1) * (subSize + gap);
                    y = subSize + 2 * gap;
                }
            } else if (count == 4) {
                x = gap + (i % 2) * (subSize + gap);
                if (i < 2) {
                    y = gap;
                } else {
                    y = subSize + 2 * gap;
                }
            } else if (count == 5) {
                if (i == 0) {
                    x = y = (size - 2 * subSize - gap) / 2.0f;
                } else if (i == 1) {
                    x = (size + gap) / 2.0f;
                    y = (size - 2 * subSize - gap) / 2.0f;
                } else if (i > 1) {
                    x = gap + (i - 2) * (subSize + gap);
                    y = (size + gap) / 2.0f;
                }
            } else if (count == 6) {
                x = gap + (i % 3) * (subSize + gap);
                if (i < 3) {
                    y = (size - 2 * subSize - gap) / 2.0f;
                } else {
                    y = (size + gap) / 2.0f;
                }
            } else if (count == 7) {
                if (i == 0) {
                    x = (size - subSize) / 2.0f;
                    y = gap;
                } else if (i < 4) {
                    x = gap + (i - 1) * (subSize + gap);
                    y = subSize + 2 * gap;
                } else {
                    x = gap + (i - 4) * (subSize + gap);
                    y = gap + 2 * (subSize + gap);
                }
            } else if (count == 8) {
                if (i == 0) {
                    x = (size - 2 * subSize - gap) / 2.0f;
                    y = gap;
                } else if (i == 1) {
                    x = (size + gap) / 2.0f;
                    y = gap;
                } else if (i < 5) {
                    x = gap + (i - 2) * (subSize + gap);
                    y = subSize + 2 * gap;
                } else {
                    x = gap + (i - 5) * (subSize + gap);
                    y = gap + 2 * (subSize + gap);
                }
            } else if (count == 9) {
                x = gap + (i % 3) * (subSize + gap);
                if (i < 3) {
                    y = gap;
                } else if (i < 6) {
                    y = subSize + 2 * gap;
                } else {
                    y = gap + 2 * (subSize + gap);
                }
            }

            float left = x;
            float top = y;
            float right = left + subSize;
            float bottom = top + subSize;
            Path path = new Path();
            path.addRect(left, top, right, bottom, Path.Direction.CW);

            Region region = new Region();
            region.setPath(path, globalRegion);

            regions[i] = region;
        }
        return regions;
    }
}
