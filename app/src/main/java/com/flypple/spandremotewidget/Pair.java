package com.flypple.spandremotewidget;

import java.io.Serializable;

/**
 * Created by qiqinglin
 * 2021/5/20
 * flypple20088@163.com
 */
public class Pair implements Serializable {
    public String key;
    public String value;

    public Pair() {
    }

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
