package com.qualityautomacao.webposto.model;

import org.json.JSONObject;

public class Token {
    private String toString;

    public int unidadeNegocio;
    public String nomeUnidade;
    public int usuario;
    public int perfil;
    public int rede;

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