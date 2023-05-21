package com.fractal.challenge.model;

public class iPair<T, L> {
    public T first;
    public L second;

    public iPair(T first, L second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public L getSecond() {
        return second;
    }

    public void setSecond(L second) {
        this.second = second;
    }
}
