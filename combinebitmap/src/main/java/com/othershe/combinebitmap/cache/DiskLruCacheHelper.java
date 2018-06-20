package com.othershe.combinebitmap.cache;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

public class DiskLruCacheHelper {
    private long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private String CACHE_DIR = "bitmap_cache";

    public DiskLruCache mDiskLruCache;

    public DiskLruCacheHelper(Context context) {
        File diskCacheDir = getDiskCacheDir(context);
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getDiskCacheDir(Context context) {
        boolean externalStorageAvailable = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + CACHE_DIR);
    }

    private long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

}
