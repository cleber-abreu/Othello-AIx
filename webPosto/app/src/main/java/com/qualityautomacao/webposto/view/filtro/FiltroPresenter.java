package com.qualityautomacao.webposto.view.filtro;

import com.qualityautomacao.webposto.model.DadosFiltro;

/**
 * Created by wiliam on 05/01/17.
 */

public interface FiltroPresenter {
    void carregaComponentes();
    void consulta(DadosFiltro dados);
}
