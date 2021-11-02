package com.chinamobile.foot.util;

import java.util.concurrent.*;

/**
 * Author:
 * Date: 2021/3/19
 * Desc: 创建单例的线程池对象的工具类
 */
public class ThreadPoolUtil {
    private static ThreadPoolExecutor pool;
    public static int BLOCKINGQUEUE_SIZE = 100;
    private static BlockingQueue<long[]> blockingQueue = new LinkedBlockingQueue<>();

    /**
     * corePoolSize 核心线程数，线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行还是放到workQueue任务队列
     * maxinumPoolSize 最大线程数
     * keepAliveTime  当线程池中空闲线程数超过corePoolSize时，多余的线程在多长时间内被销毁
     * unit  keepAliveTime单位
     * workQueue 工作队列，被添加到线程池中，但尚未被执行的任务
     * @return
     */
    public static ThreadPoolExecutor getIntance(){
        if(pool == null){
            synchronized (ThreadPoolExecutor.class){
                if(pool == null){
                    pool = new ThreadPoolExecutor(
                            4,10,10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE)
                    );
                }
            }

        }

        return pool;
    }


    public static BlockingQueue<long[]> getBlockingQueue(){
        return blockingQueue;
    }

}
