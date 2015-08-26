package com.qualityautomacao.webposto.utils;

public interface Consumer<T> {
    void accept(T t) throws Exception;
}