package com.qualityautomacao.webposto.utils;

import android.content.Context;

import org.json.JSONObject;

public class Request {

    private final Context context;
    private final String funcao;

    private int opcoes = -1;
    private String dados = "{}";
    private Runnable onPreExecute;
    private Runnable onPosExecute;
    private Consumer<JSONObject> consumer;
    private Runnable onTimeout;

    public Request(Context context, String funcao) {
        this.context = context;
        this.funcao = funcao;
    }

    public Request setFlags(int opcoes) {
        this.opcoes = opcoes;
        return this;
    }

    public Request setDados(String dados) {
        this.dados = dados;
        return this;
    }

    public Request onPreExecute(Runnable onPreExecute) {
        this.onPreExecute = onPreExecute;
        return this;
    }

    public Request onPosExecute(Runnable onPosExecute) {
        this.onPosExecute = onPosExecute;
        return this;
    }

    public Request onTimeout(Runnable onTimeout) {
        this.onTimeout = onTimeout;
        return this;
    }

    public Request onCompleteRequest(Consumer<JSONObject> consumer) {
        this.consumer = consumer;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public String getFuncao() {
        return funcao;
    }

    public int getOpcoes() {
        return opcoes;
    }

    public String getDados() {
        return dados;
    }

    public Runnable getOnPreExecute() {
        return onPreExecute;
    }

    public Runnable getOnPosExecute() {
        return onPosExecute;
    }

    public Consumer<JSONObject> getConsumer() {
        return consumer;
    }

    public Runnable getOnTimeout() {
        return onTimeout;
    }
}
