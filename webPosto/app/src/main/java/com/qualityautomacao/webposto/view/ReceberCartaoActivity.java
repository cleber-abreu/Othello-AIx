package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.ReceberCartaoAdapter;
import com.qualityautomacao.webposto.adapter.SeparadorLista;
import com.qualityautomacao.webposto.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceberCartaoActivity extends BaseActivity {

    @BindView(R.id.rcar_rec_titulos) RecyclerView recCartao;

    private JSONObject pacoteDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_cartao);super.addToolbar(true);
        ButterKnife.bind(this);

        try {
            pacoteDados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO));
        } catch (JSONException e) {
            pacoteDados = new JSONObject();
        }

        recCartao.setLayoutManager(new LinearLayoutManager(this));
        recCartao.addItemDecoration(new SeparadorLista(ContextCompat.getDrawable(this, R.drawable.separador_lista), pacoteDados.optJSONArray("DAD").length()));
        recCartao.setAdapter(new ReceberCartaoAdapter(this, pacoteDados));
    }
}
