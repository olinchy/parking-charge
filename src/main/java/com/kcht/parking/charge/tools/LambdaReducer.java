package com.kcht.parking.charge.tools;


public interface LambdaReducer<T> {
    T reduce(T x, T y);
}
