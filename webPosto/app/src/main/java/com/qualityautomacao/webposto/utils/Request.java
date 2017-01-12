package com.qualityautomacao.webposto.utils;

import android.content.Context;

import org.json.JSONObject;

public class Request {

    private final Context context;
    private final String funcao;
    private final String rotina;

    private String dados = "{}";
    private final Consumer<JSONObject> onSucesso;
    private final Consumer<String> onFalha;

    public Request(Context context, String funcao, Consumer<JSONObject> onSucesso) {
        this(context, funcao, onSucesso, new Consumer<String>() { @Override public void accept(String s){}});
    }

    public Request(Context context, String funcao, Consumer<JSONObject> onSucesso, Consumer<String> onFalha) {
        this(context, "MOBILE_", funcao, onSucesso, onFalha);
    }

    public Request(Context context, String rotina, String funcao, Consumer<JSONObject> onSucesso) {
        this(context, rotina, funcao, onSucesso, new Consumer<String>() { @Override public void accept(String s){}});
    }

    public Request(Context context, String rotina, String funcao, Consumer<JSONObject> onSucesso, Consumer<String> onFalha) {
        this.context = context;
        this.rotina = rotina;
        this.funcao = funcao;
        this.onSucesso = onSucesso;
        this.onFalha = onFalha;
    }

    public Request setDados(String dados) {
        this.dados = dados;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getRotina(){
        return this.rotina;
    }

    public String getDados() {
        return dados;
    }

    public Consumer<JSONObject> getOnSucesso() {
        return onSucesso;
    }

    public Consumer<String> getOnFalha() {
        return onFalha;
    }
}
