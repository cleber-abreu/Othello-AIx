package com.qualityautomacao.webposto.view;

public final class Notificacao {
    private final String titulo;
    private final String detalhe;
    private final String data;
    private final int codigo;
    private boolean lida;

    public Notificacao(String titulo, String detalhe, String data, int codigo, boolean lida) {
        this.titulo = titulo;
        this.detalhe = detalhe;
        this.data = data;
        this.codigo = codigo;
        this.lida = lida;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public String getData() {
        return data;
    }

    public int getCodigo() {
        return codigo;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
