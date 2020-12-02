package com.hly.httpRequest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    //通过阻塞式队列来存储任务
    private LinkedBlockingQueue<Runnable> mWorkQueue = new LinkedBlockingQueue<>();
    //把队列中的任务放到线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    //单例模式
    private static class InstanceHolder {
        public static ThreadPoolManager sInstance = new ThreadPoolManager();
    }

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        //运行线程池
        mThreadPoolExecutor.execute(runnable);
    }

    public static ThreadPoolManager getSingleInstance() {
        return InstanceHolder.sInstance;
    }

    //添加任务
    public void execute(Runnable runnable) {
        if (runnable != null) {
            try {
                mWorkQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //当线程数超过maxPoolSize或者keep-alive时间超时时执行拒绝策略
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        /**
         * @param runnable 超时被线程池抛弃的线程
         * @param threadPoolExecutor
         */
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            //将该线程重新放入请求队列中
            try {
                mWorkQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    //工作起来,不断的取请求线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                //不断从从请求队列中取出请求
                try {
                    runnable = mWorkQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果不为空，放入线程池中执行
                if (runnable != null) {
                    mThreadPoolExecutor.execute(runnable);
                }
            }

        }
    };

}
