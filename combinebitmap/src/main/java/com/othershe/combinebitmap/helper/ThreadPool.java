package com.othershe.combinebitmap.helper;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义的下载线程池
 */
public class ThreadPool {
    //CPU核心数
    private int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //可同时下载的任务数（核心线程数）
    private int CORE_POOL_SIZE = CPU_COUNT;
    //缓存队列的大小（最大线程数）
    private int MAX_POOL_SIZE = 2 * CPU_COUNT + 1;
    //非核心线程闲置的超时时间（秒），如果超时则会被回收
    private long KEEP_ALIVE = 10L;

    private ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    private ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger();

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "download_task#" + mCount.getAndIncrement());
        }
    };

    private ThreadPool() {
    }

    public static ThreadPool getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final ThreadPool instance = new ThreadPool();
    }

    private ThreadPoolExecutor getThreadPoolExecutor() {
        if (THREAD_POOL_EXECUTOR == null) {
            THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAX_POOL_SIZE,
                    KEEP_ALIVE,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(),
                    sThreadFactory);
        }
        return THREAD_POOL_EXECUTOR;
    }

    public void execute(Runnable command) {
        getThreadPoolExecutor().execute(command);
    }
}
