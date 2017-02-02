package com.qualityautomacao.webposto.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wiliam on 05/01/17.
 */

public abstract class UtilsDate {

    public static final String dd_MM_yyyy = "dd/MM/yyyy";
    private static final String[] DIAS_SEMANA = {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};

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

    public static boolean intervaloValido(String dataInicio, String dataFim, String formato){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            return !sdf.parse(dataFim).before(sdf.parse(dataInicio));
        }catch (Exception e){
            Log.e("WEB_POSTO_LOG", "intervaloValido: " + e.getMessage());
            return false;
        }
    }

    public static String diaDaSemana(){
        return new SimpleDateFormat("EEEE").format(new Date());
    }
}
