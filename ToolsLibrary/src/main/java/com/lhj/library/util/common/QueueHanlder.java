package com.lhj.library.util.common;

import android.os.Handler;

import java.util.ArrayList;

/**
 * 先进先出的队列
 * Created by Administrator on 2016/12/21.
 */
public class QueueHanlder<T> {
    private ArrayList<T> list;

    public QueueHanlder(){
      list = new ArrayList<T>();
    }

    public void addQueue(T t){
        list.add(t);
    }

    public T getEntry(){
        return list.get(0);
    }

    public void removeEntry(){
        list.remove(0);
    }



}
