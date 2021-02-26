package com.github.iceant.point.core.utils.converter;

public interface IConverter<A, B> {
    public B convertFromAToB(A a, B def);
}