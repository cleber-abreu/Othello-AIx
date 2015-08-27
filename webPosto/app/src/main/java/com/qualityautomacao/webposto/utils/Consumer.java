package com.qualityautomacao.webposto.utils;

import java.io.Serializable;

public interface Consumer<T> extends Serializable {
    void accept(T t) throws Exception;
}