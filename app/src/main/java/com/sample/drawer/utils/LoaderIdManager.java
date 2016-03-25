package com.sample.drawer.utils;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Id manager for multiple loaders
 */
public class LoaderIdManager {
    private LinkedHashSet<Integer> free, used;
    private int max;

    public LoaderIdManager() {
        free = new LinkedHashSet<>();
        used = new LinkedHashSet<>();
        max = 1;
        free.add(max);
    }

    public int grabId(){
        int id = free.iterator().next();
        free.remove(id);
        if (free.isEmpty()){
            max++;
            free.add(max);
        }
        used.add(id);
        return id;
    }

    public void releaseId(int id){
        if (used.contains(id)){
            used.remove(id);
            free.add(id);
        }
    }
}
