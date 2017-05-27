package com.skyworthauto.navi.util;

import java.util.ArrayList;


public class Stack<T> extends ArrayList<T> {

    public Stack() {
        super();
    }

    public Stack(int size) {
        super(size);
    }

    public void push(T object) {
        add(object);
    }

    public T pop() {
        if (size() > 0) {
            return remove(size() - 1);
        }

        return null;
    }

    public T peek() {
        if (size() > 0) {
            return get(size() - 1);
        }

        return null;
    }
}
