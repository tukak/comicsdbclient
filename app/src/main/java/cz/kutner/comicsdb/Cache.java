package cz.kutner.comicsdb;

import android.support.v4.util.LruCache;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 12.5.2015.
 */

public class Cache {

    private static Cache instance;
    private LruCache<Object, Object> lru;

    private Cache() {

        lru = new LruCache<Object, Object>(1024);

    }

    public static Cache getInstance() {

        if (instance == null) {

            instance = new Cache();
        }

        return instance;

    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }
}
