package com.kcht.parking.charge.tools;

/**
 * Created by olinchy on 16-2-19.
 */
public interface LambdaConverter<T, C> {
    T to(C content);
}
