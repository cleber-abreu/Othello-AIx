package com.qualityautomacao.webposto.utils;

import android.content.Context;

import org.json.JSONObject;

public class Request {

    private final Context context;
    private final String funcao;
    private String rotina;

    private int opcoes = -1;
    private String dados = "{}";
    private Consumer<JSONObject> onSucesso;
    private Consumer<String> onFalha;

    public Request(Context context, String funcao, Consumer<JSONObject> onSucesso) {
        this(context, funcao, onSucesso, new Consumer<String>() { @Override public void accept(String s){}});
    }

    public Request(Context context, String funcao, Consumer<JSONObject> onSucesso, Consumer<String> onFalha) {
        this(context, "MOBILE", funcao, onSucesso, onFalha);
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

    public Request setFlags(int opcoes) {
        this.opcoes = opcoes;
        return this;
    }

    public Request setDados(String dados) {
        this.dados = dados;
        return this;
    }

    public Request setRotina(String rotina){
        this.rotina = rotina;
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

    public int getOpcoes() {
        return opcoes;
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
