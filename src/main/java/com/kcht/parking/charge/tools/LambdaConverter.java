package com.kcht.parking.charge.tools;

public interface LambdaConverter<T, C> {
    T to(C content);
}
