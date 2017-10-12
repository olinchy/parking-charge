package com.kcht.parking.charge.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class To {
    public static <T, C> List<T> map(Collection<C> content, LambdaConverter<T, C> func) {
        ArrayList<T> list = new ArrayList<T>();
        for (C c : content) {
            list.add(func.to(c));
        }
        return list;
    }

    public static <T> List<T> filter(Collection<T> content, LambdaFilter<T> func) {
        ArrayList<T> list = new ArrayList<T>();
        for (T t : content) {
            if (func.isAccept(t)) {
                list.add(t);
            }
        }
        return list;
    }

    public static <T> T reduce(Collection<T> content, LambdaReducer<T> func) {
        ArrayList<T> list = new ArrayList<T>(content);
        T result = list.get(list.size() - 1);
        for (int i = list.size() - 2; i >= 0; i--) {
            result = func.reduce(result, list.get(i));
        }
        return result;
    }

    public static <T> void for_each(Collection<T> content, LambdaFunc<T> func) {
        for (T t : content) {
            func.exec(t);
        }
    }

    public static <T> List<T> limit(final List<T> tList, final int count) {
        int toIndex = count;
        if (tList.size() < count) {
            toIndex = tList.size();
        }
        return tList.subList(0, toIndex);
    }

    public static <T> T max(final List<T> list, Comparator<T> comparator) {
        Collections.sort(list, comparator);
        return list.get(list.size() - 1);
    }
}
