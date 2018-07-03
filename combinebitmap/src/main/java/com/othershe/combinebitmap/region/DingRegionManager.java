package com.othershe.combinebitmap.region;

import android.graphics.Path;
import android.graphics.Region;

public class DingRegionManager implements IRegionManager {
    @Override
    public Region[] calculateRegion(int size, int subSize, int gap, int count) {
        Region[] regions = new Region[count];
        Region globalRegion = new Region(0, 0, size, size);

        int[][] dxy = {{0, 0}, {1, 0}, {1, 1}, {0, 1}};

        for (int i = 0; i < count; i++) {
            float width = size;
            float height = size;

            if (count == 2 || (count == 3 && i == 0)) {
                width = (size - gap) / 2;
                height = size;
            } else if ((count == 3 && (i == 1 || i == 2)) || count == 4) {
                width = (size - gap) / 2;
                height = (size - gap) / 2;
            }

            int dx = dxy[i][0];
            int dy = dxy[i][1];

            float left = dx * (size + gap) / 2.0f;
            float top = dy * (size + gap) / 2.0f;
            float right = left + width;
            float bottom = top + height;
            Path path = new Path();
            path.addRect(left, top, right, bottom, Path.Direction.CW);

            Region region = new Region();
            region.setPath(path, globalRegion);

            regions[i] = region;
        }
        return regions;
    }
}
