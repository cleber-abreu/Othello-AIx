package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.SeparadorLista;
import com.qualityautomacao.webposto.adapter.VendaAdapter;
import com.qualityautomacao.webposto.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VendasActivity extends BaseActivity {

    @BindView(R.id.ven_rec_vendas) RecyclerView recVendas;

    private JSONObject pacoteDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);
        super.addToolbar(true);
        ButterKnife.bind(this);

        try {
            pacoteDados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO));
        } catch (JSONException e) {
            pacoteDados = new JSONObject();
        }

        recVendas.setLayoutManager(new LinearLayoutManager(this));
        recVendas.addItemDecoration(new SeparadorLista(ContextCompat.getDrawable(this, R.drawable.separador_lista), pacoteDados.optJSONArray("DAD").length()));
        recVendas.setAdapter(new VendaAdapter(this, pacoteDados));
    }
}