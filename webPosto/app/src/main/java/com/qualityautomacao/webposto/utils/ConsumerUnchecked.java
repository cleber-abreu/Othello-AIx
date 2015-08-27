package com.qualityautomacao.webposto.utils;

import java.io.Serializable;

public interface ConsumerUnchecked<T> extends Serializable {
    void accept(T t);
}