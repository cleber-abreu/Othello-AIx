package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qualityautomacao.webposto.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void contasPagar(View view) {
        startActivity(new Intent(this, ContasPagarActivity.class));
    }

    public void contasReceber(View view) {
        startActivity(new Intent(this, ContasReceberActivity.class));
    }

    public void vendas(View view) {
        startActivity(new Intent(this, VendasActivity.class));
    }

    public void tanques(View view) {
        startActivity(new Intent(this, TanquesActivity.class));
    }

    public void trocaPreco(View view) {
        startActivity(new Intent(this, TrocaPrecoActivity.class));
    }

    public void projecaoAbastecimento(View view) {
        startActivity(new Intent(this, ProjecaoAbastecimentoActivity.class));
    }

    public void notificacoes(View view) {
        startActivity(new Intent(this, NotificacoesActivity.class));
    }
}
