package com.qualityautomacao.webposto.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

public class TabAction {
    public final String descricao;
    public final Consumer<View> acao;

    public TabAction(Context context, @StringRes int descricao, Consumer<View> acao) {
        this.descricao = context.getString(descricao);
        this.acao = acao;
    }
}
