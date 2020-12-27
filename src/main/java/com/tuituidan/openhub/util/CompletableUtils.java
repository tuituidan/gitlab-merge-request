package com.tuituidan.openhub.util;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.guava.ThreadFactoryBuilder;

/**
 * CompletableUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@UtilityClass
@Slf4j
public class CompletableUtils {

    /**
     * 任务等待队列 容量
     */
    private static final int TASK_QUEUE_SIZE = 1000;
    /**
     * 空闲线程存活时间 单位分钟
     */
    private static final long KEEP_ALIVE_TIME = 10L;

    /**
     * 线程名字前缀.
     */
    private static final String THREAD_NAME_PREFIX = "gtilab-merge-request";

    /**
     * 任务执行线程池
     */
    private static ThreadPoolExecutor threadPool;

    static {
        int corePoolNum = 2 * Runtime.getRuntime().availableProcessors() + 1;
        int maximumPoolSize = 2 * corePoolNum;
        threadPool = new ThreadPoolExecutor(corePoolNum, maximumPoolSize, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(TASK_QUEUE_SIZE),
                new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_PREFIX + "-%d").build(), (r, executor) -> {
            if (!executor.isShutdown()) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException ex) {
                    log.error("线程加入队列失败", ex);
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    /**
     * runAsync.
     *
     * @param task task
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> runAsync(Runnable task) {
        return CompletableFuture.runAsync(task, threadPool);
    }

    /**
     * waitAll.
     *
     * @param futures futures
     */
    public static void waitAll(List<CompletableFuture<?>> futures) {
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
