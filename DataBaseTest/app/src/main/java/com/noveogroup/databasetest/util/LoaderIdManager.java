package com.noveogroup.databasetest.util;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by Warinator on 25.03.2016.
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
