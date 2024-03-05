package com.beginsecure.domain;

import java.util.*;
public class LimitedSizeHashMap<K, V> extends HashMap<K, V> {
    private final int maxSize;

    public LimitedSizeHashMap(int maxSize) {
        super();
        this.maxSize = maxSize;
    }
    //this method is overrided to check if the size of the hashmap is greater than 50 or not
    @Override
    public V put(K key, V value) {
        if (size() >= maxSize) {

            System.out.println("Size limit reached. Unable to add more than 50 elements.");
            return null;
        }

        return super.put(key, value);
    }


}
