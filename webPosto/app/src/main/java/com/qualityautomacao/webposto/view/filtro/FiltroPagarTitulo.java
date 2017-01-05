package com.qualityautomacao.webposto.view.filtro;

import android.content.Intent;

import com.qualityautomacao.webposto.utils.UtilsDate;
import com.qualityautomacao.webposto.view.FiltroActivity;
import com.qualityautomacao.webposto.view.TituloPagarActivity;

/**
 * Created by wiliam on 05/01/17.
 */

public class FiltroPagarTitulo implements FiltroPresenter {

    private FiltroActivity activity;

    public FiltroPagarTitulo(FiltroActivity activity){
        this.activity = activity;
    }

    @Override
    public void carregaComponentes() {
        activity.initDatePicker(UtilsDate.getHoje(UtilsDate.dd_MM_yyyy),
                UtilsDate.getDataIncMes(UtilsDate.dd_MM_yyyy, 1));
        activity.initEstadoConta();
    }

    @Override
    public void consulta() {
        activity.startActivity(new Intent(activity, TituloPagarActivity.class));
    }
}
