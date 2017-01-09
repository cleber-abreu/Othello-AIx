package com.qualityautomacao.webposto.model;

/**
 * Created by wiliam on 05/01/17.
 */
public class DadosFiltro {
    private String bomparaMovimento;
    private String dataInicio;
    private String dataFim;
    private String estadoConta;
    private String estadoContaAP;
    private String estadoContaAR;
    private boolean porCliente;

    public String getBomparaMovimento() {
        return bomparaMovimento;
    }

    public void setBomparaMovimento(String bomparaMovimento) {
        this.bomparaMovimento = bomparaMovimento;
    }

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

    public String getEstadoContaAP() {
        return estadoContaAP;
    }

    public void setEstadoContaAP(String estadoContaAP) {
        this.estadoContaAP = estadoContaAP;
    }

    public String getEstadoContaAR() {
        return estadoContaAR;
    }

    public void setEstadoContaAR(String estadoContaAR) {
        this.estadoContaAR = estadoContaAR;
    }

    public boolean getPorCliente() {
        return porCliente;
    }

    public void setPorCliente(boolean porCliente) {
        this.porCliente = porCliente;
    }
}
