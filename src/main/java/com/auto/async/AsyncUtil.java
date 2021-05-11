package com.auto.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public class AsyncUtil {


    private static ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(4, new ThreadFactoryBuilder().
            setUncaughtExceptionHandler((Thread thread1, Throwable e) -> log.error("异步任务异常!", e)).build());


    /**
     * 异步线程调用模板代码
     */
    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(() -> {
            runnable.run();
        });
    }

    /**
     * 异步线程调用模板代码
     */
    public static <V> CompletableFuture<V> callAsync(Callable<V> callable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return callable.call();
            } catch (Exception e) {
                log.error("异常", e);
            }
            return null;
        });
    }


    /**
     * 延迟异步线程调用模板
     */
    public static void runAsyncDelay(Runnable runnable, long delay, TimeUnit unit) {
        scheduleService.schedule(() -> {
            runnable.run();
        }, delay, unit);
    }


    /**
     * 异步线程调用模板
     */
    public static <T> void waitAsync(ExecutorService executorPool, Collection<T> suppliers, Consumer<T> asyncAction) {
        if (CollectionUtils.isEmpty(suppliers)) {
            return;
        }
        List<Callable<Object>> collect = suppliers.stream().map(m -> (Callable<Object>) () -> {
            Thread currentThread = Thread.currentThread();
            String oriThreadName = currentThread.getName();
            try {
                asyncAction.accept(m);
                return new Object();
            } catch (Exception e) {
                return null;
            } finally {
                currentThread.setName(oriThreadName);
            }
        }).collect(Collectors.toList());
        if(collect==null){return;}
        try {
            executorPool.invokeAll(collect);
        } catch (InterruptedException e) {
            Thread.interrupted();
            e.printStackTrace();
        }

    }

}
