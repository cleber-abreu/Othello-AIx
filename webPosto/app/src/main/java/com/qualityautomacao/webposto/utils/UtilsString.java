package com.qualityautomacao.webposto.utils;

import java.text.DecimalFormat;

public abstract class UtilsString {
    private static final DecimalFormat formatter = new DecimalFormat("R$ #,##0.00");

    public static String substring(String string, int inicio, int fim) {
        return string.length() < fim ? string.substring(inicio) : string.substring(inicio, fim);
    }

    public static String abreviar(String string, int quantidadePorPalavra) {
        final String[] split = string.split(" ");
        final StringBuilder retorno = new StringBuilder(string.length());

        for (String s : split)
            retorno.append(substring(s, 0, quantidadePorPalavra))
                    .append(". ");

        return retorno.toString().trim();
    }

    public static String formatarMonetario(double valor){
        return formatter.format(valor);
    }
}
