package com.lhj.library.util.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ExecutorsSingLeThread {
    private static ExecutorsSingLeThread instance = new ExecutorsSingLeThread();
    private ExecutorService mES =  Executors.newSingleThreadExecutor();
    public static ExecutorsSingLeThread getInstance(){return instance;};
    public void enqueue(Runnable runnable){
       mES.execute(runnable);
    }
}
