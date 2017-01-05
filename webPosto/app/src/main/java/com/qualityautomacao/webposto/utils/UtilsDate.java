package com.qualityautomacao.webposto.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by wiliam on 05/01/17.
 */

public abstract class UtilsDate {

    public static final String dd_MM_yyyy = "dd/MM/yyyy";

    public static String getHoje(String formato){
        return new SimpleDateFormat(formato).format(Calendar.getInstance().getTime());
    }

    public static String getDataIncDia(String formato, int incrementoDias){
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_YEAR, incrementoDias);
        return new SimpleDateFormat(formato).format(calendario.getTime());
    }

    public static String getDataIncMes(String formato, int incrementoMes){
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.MONTH, incrementoMes);
        return new SimpleDateFormat(formato).format(calendario.getTime());
    }
}
