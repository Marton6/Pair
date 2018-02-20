package com.marton.pair.quiz;

import java.io.Serializable;

/**
 * Created by marton on 12/13/17.
 */

public class Pair implements Serializable {
    private String first, second;

    public Pair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
