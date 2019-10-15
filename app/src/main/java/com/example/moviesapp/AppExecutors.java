package com.example.moviesapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if (instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
