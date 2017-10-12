package com.kcht.parking.charge.tools;

public interface LambdaFilter<T> {
    boolean isAccept(T t);
}
