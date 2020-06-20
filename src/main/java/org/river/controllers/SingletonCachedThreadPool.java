package org.river.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The singleton cached thread pool is used to create a
 * cached thread pool and promise that there is only one
 * cached thread pool in multithreading environment.
 *
 * @author - Haribo
 */
public class SingletonCachedThreadPool {
    private static ExecutorService cachedThreadPool;

    /**
     * Instead of block the whole method, in order to increase performance,
     * we use synchronized modifier only when the object are going to create.
     *
     * @return cachedThreadPool
     */
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
