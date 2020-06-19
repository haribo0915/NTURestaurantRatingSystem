package org.river.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author - Haribo
 */
public class SingletonCachedThreadPool {
    private static ExecutorService cachedThreadPool;

    // Instead of block the whole method, in order to increase performance,
    // we use synchronized modifier only when the object are going to create.
    public static ExecutorService getInstance() {
        if (cachedThreadPool == null) {
            synchronized(SingletonCachedThreadPool.class) {
                if (cachedThreadPool == null) {
                    cachedThreadPool = Executors.newCachedThreadPool();
                }
            }
        }
        return cachedThreadPool;
    }
}
