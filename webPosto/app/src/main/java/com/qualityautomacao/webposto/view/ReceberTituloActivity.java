package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.SeparadorLista;
import com.qualityautomacao.webposto.adapter.TituloAdapter;
import com.qualityautomacao.webposto.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceberTituloActivity extends BaseActivity {

    @BindView(R.id.rect_rec_titulos) RecyclerView recTitulos;

    private JSONObject pacoteDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_titulo);
        super.addToolbar(true);
        ButterKnife.bind(this);

        try {
            pacoteDados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO));
        } catch (JSONException e) {
            pacoteDados = new JSONObject();
        }

        recTitulos.setLayoutManager(new LinearLayoutManager(this));
        recTitulos.addItemDecoration(new SeparadorLista(ContextCompat.getDrawable(this, R.drawable.separador_lista), pacoteDados.optJSONArray("DAD").length()));
        recTitulos.setAdapter(new TituloAdapter(this, pacoteDados));

        Log.i("WEB_POSTO_LOG", "onCreate: " + getIntent().getStringExtra(Constantes.EXTRA_DADO));
    }
}
