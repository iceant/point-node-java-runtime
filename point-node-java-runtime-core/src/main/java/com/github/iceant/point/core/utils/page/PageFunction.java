package com.github.iceant.point.core.utils.page;

public interface PageFunction {
    <T> Page<T> getPage(PageRequest pageRequest);
}