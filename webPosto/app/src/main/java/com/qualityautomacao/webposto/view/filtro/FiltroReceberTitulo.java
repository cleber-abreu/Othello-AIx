package com.qualityautomacao.webposto.view.filtro;

import com.qualityautomacao.webposto.model.DadosFiltro;
import com.qualityautomacao.webposto.view.FiltroActivity;

/**
 * Created by wiliam on 06/01/17.
 */

public class FiltroReceberTitulo implements FiltroPresenter {
    private FiltroActivity activity;

    public FiltroReceberTitulo(FiltroActivity activity){
        this.activity = activity;
    }

    @Override
    public void carregaComponentes() {

    }

    @Override
    public void consulta(DadosFiltro dados) {

    }
}
