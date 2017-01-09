package com.qualityautomacao.webposto.view.filtro;

import com.qualityautomacao.webposto.model.DadosFiltro;
import com.qualityautomacao.webposto.utils.UtilsDate;
import com.qualityautomacao.webposto.view.FiltroActivity;

/**
 * Created by wiliam on 06/01/17.
 */

public class FiltroReceberCartaFrete implements FiltroPresenter {
    private FiltroActivity activity;

    public FiltroReceberCartaFrete(FiltroActivity activity){
        this.activity = activity;
    }

    @Override
    public void carregaComponentes() {
        activity.initBomparaMovimento();
        activity.initDatePicker(
                UtilsDate.getHoje(UtilsDate.dd_MM_yyyy),
                UtilsDate.getDataIncMes(UtilsDate.dd_MM_yyyy, 1));
        activity.initEstadoContaAR();
        activity.initCliente();
    }

    @Override
    public void consulta(DadosFiltro dados) {

    }
}
