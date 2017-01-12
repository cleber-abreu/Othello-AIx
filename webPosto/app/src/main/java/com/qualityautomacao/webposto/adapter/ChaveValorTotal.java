package com.qualityautomacao.webposto.adapter;

/**
 * @author Wiliam
 * @since 12/01/2017
 */
public interface ChaveValorTotal {
    int getSize();
    boolean isTotal(int position);
    String labelTotal();
    String valorTotal();
    String labelDado(int position);
    String valorDado(int position);
}
