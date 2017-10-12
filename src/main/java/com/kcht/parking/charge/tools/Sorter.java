package com.kcht.parking.charge.tools;

import java.util.Comparator;

/**
 * Created by olinchy on 7/25/14 for MO_JAVA.
 */
public class Sorter {
    public static <T> void asc(T[] toSort, Comparator<T> comparator) {
        for (int i = 0; i < toSort.length - 1; i++) {
            for (int j = 0; j < toSort.length - i - 1; j++) {
                if (comparator.compare(toSort[j], toSort[j + 1]) > 0) {
                    swap(toSort, j, j + 1);
                }
            }
        }
    }

    private static <T> void swap(T[] toSort, int j, int i) {
        T temp = toSort[j];
        toSort[j] = toSort[i];
        toSort[i] = temp;
    }
}
