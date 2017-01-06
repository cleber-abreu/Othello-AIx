package com.qualityautomacao.webposto.model;

/**
 * Created by wiliam on 05/01/17.
 */

public class DadosFiltro {
    private String dataInicio;
    private String dataFim;
    private String estadoConta;

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getEstadoConta() {
        return estadoConta;
    }

    public void setEstadoConta(String estadoConta) {
        this.estadoConta = estadoConta;
    }
}
