package org.river.models;

import java.util.Enumeration;
import java.util.Hashtable;

/**
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
    synchronized void takeIn(T object) {
        lock.remove(object);
        unlock.put(object, System.currentTimeMillis());
    }
}
