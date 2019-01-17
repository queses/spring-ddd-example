package com.qss.adddvert.core.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayListFactory {
    public static <T> ArrayList<T> from(T obj) {
        ArrayList<T> list = new ArrayList<>();
        list.add(obj);
        return list;
    }

    public static <T> ArrayList<T> clone(List<T> oldList, T obj) {
        ArrayList<T> clone = new ArrayList<>(oldList);
        clone.add(obj);
        return clone;
    }
}
