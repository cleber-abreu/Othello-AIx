package com.qualityautomacao.webposto.model;

import org.json.JSONObject;

import java.io.Serializable;

public final class Token implements Serializable {
    private String toString;

    public final int unidadeNegocio;
    public final String nomeUnidade;
    public final int usuario;
    public final int perfil;
    public final int rede;

    public Token(final int unidadeNegocio, final String nomeUnidade, final int usuario, final int perfil, final int rede) {
        this.unidadeNegocio = unidadeNegocio;
        this.nomeUnidade = nomeUnidade;
        this.usuario = usuario;
        this.perfil = perfil;
        this.rede = rede;
    }

    @Override
    public String toString() {
        try {
            return toString == null ?
                    toString = new JSONObject()
                    .put("TOK_CD_USUARIO", usuario)
                    .put("TOK_CD_PERFIL", perfil)
                    .put("TOK_FL_LOG", "N")
                    .put("TOK_CD_UNIDADE_NEGOCIO", unidadeNegocio)
                    .put("TOK_CD_REDE", rede)
                    .toString() :
                    toString;
        } catch (Exception e) {
            return "null";
        }
    }
}