package com.qualityautomacao.webposto.utils;

import android.view.View;

public class TabAction {
    public final String descricao;
    public final ConsumerUnchecked<View> acao;

    public TabAction(String descricao, ConsumerUnchecked<View> acao) {
        this.descricao = descricao;
        this.acao = acao;
    }
}
