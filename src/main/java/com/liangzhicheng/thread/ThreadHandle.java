package com.liangzhicheng.thread;

import com.liangzhicheng.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadHandle {

    public void execute(){
        List<String> updateList = new ArrayList<>();
        //初始化线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                20,
                50,
                4,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());
        //大集合拆分多个小集合，subListLength不适于过大，看情况而定，保证多线程异步执行，过大容易回到单线程
        List<List<String>> splitList = ListUtil.split(ListUtil.buildList(), 100);
        //记录单个任务执行次数
        CountDownLatch countDownLatch = new CountDownLatch(splitList.size());
        //对拆分的集合进行批量处理，先拆分集合在多线程执行
        for(List<String> list : splitList){
            threadPool.execute(new Thread(new Runnable(){
                @Override
                public void run(){
                    for(String str : list){
                        //封装每一条记录，做为批量更新集合
                        updateList.add(str);
                    }
                }
            }));
            //任务次数-1，直至为0时唤醒await()
            countDownLatch.countDown();
        }
        try{
            //让当前线程处于阻塞状态，直到任务次数为零时唤起
            countDownLatch.await();
        }catch(InterruptedException e){
            //抛出异常
            e.printStackTrace();
        }
        //批量更新集合
        if(updateList != null && updateList.size() > 0){
//            batchUpdate(updateList);
        }
    }

}
