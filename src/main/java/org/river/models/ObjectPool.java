package org.river.models;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The object pool is used to maintain a collection of objects and reuse them to
 * improve performance in our multithreading environment. It will create new
 * object if there isn't any free one in the pool; otherwise it will reuse the
 * old object
 *
 * @author - Haribo
 */
public abstract class ObjectPool<T> {
    long deadTime;

    Hashtable<T, Long> lock, unlock;

    ObjectPool() {
        // 50 second
        deadTime = 50000;
        lock = new Hashtable<T, Long>();
        unlock = new Hashtable<T, Long>();
    }

    abstract T create();

    abstract boolean validate(T object);

    abstract void dead(T object);

    /**
     * When we take out the object from object pool, we will first check whether
     * there is any object in unlocked list and whether it has expired. We take
     * a object from unlocked list if succeed and put it in locked list; otherwise
     * we create a new object and store it in locked list. In addition, we use
     * synchronized modifier to avoid race condition
     *
     * @return object
     */
    synchronized T takeOut() {
        long now = System.currentTimeMillis();
        T object;
        if (unlock.size() > 0) {
            Enumeration<T> e = unlock.keys();
            while (e.hasMoreElements()) {
                object = e.nextElement();
                if ((now - unlock.get(object)) > deadTime) {
                    // object has dead
                    unlock.remove(object);
                    dead(object);
                    object = null;
                } else {
                    if (validate(object)) {
                        unlock.remove(object);
                        lock.put(object, now);
                        return (object);
                    } else {
                        // object failed validation
                        unlock.remove(object);
                        dead(object);
                        object = null;
                    }
                }
            }
        }
        // no objects available, create a new one
        object = create();
        lock.put(object, now);
        return (object);
    }

    /**
     * Return the object after finishing the operation with that object
     *
     * @param object
     */
    synchronized void takeIn(T object) {
        lock.remove(object);
        unlock.put(object, System.currentTimeMillis());
    }
}
