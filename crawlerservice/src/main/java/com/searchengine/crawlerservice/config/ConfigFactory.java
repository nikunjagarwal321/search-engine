package com.searchengine.crawlerservice.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ConfigFactory implements ApplicationListener<ContextRefreshedEvent> {

    final Map<String, ThreadPoolExecutor> threadPoolExecutorMap = new HashMap<>();

    public ThreadPoolExecutor getThreadPool(String retailerName) {
        if (threadPoolExecutorMap.containsKey(retailerName)) {
            return threadPoolExecutorMap.get(retailerName);
        }
        return null;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        threadPoolExecutorMap.put("crawlWorker", threadPoolExecutor);

    }

}
