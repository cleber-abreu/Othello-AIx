package com.qualityautomacao.webposto.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

public class TabAction {
    public final String descricao;
    public final ConsumerUnchecked<View> acao;

    public TabAction(Context context, @StringRes int descricao, ConsumerUnchecked<View> acao) {
        this.descricao = context.getString(descricao);
        this.acao = acao;
    }
}
