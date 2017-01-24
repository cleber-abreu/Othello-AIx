package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.DadoTotalAdapter;
import com.qualityautomacao.webposto.adapter.SeparadorLista;
import com.qualityautomacao.webposto.adapter.TituloAdapter;
import com.qualityautomacao.webposto.utils.Constantes;
import com.qualityautomacao.webposto.utils.UtilsString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceberCartaFreteActivity extends BaseActivity {

    @BindView(R.id.rcf_rec_titulos) RecyclerView recTitulos;

    private JSONObject pacoteDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_carta_frete);
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
    }
}
