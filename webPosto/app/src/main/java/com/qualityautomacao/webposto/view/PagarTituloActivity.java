package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.ChaveEvidenciaValorSimplesComTotalAdapter;
import com.qualityautomacao.webposto.adapter.ChaveValorTotal;
import com.qualityautomacao.webposto.adapter.SeparadorLista;
import com.qualityautomacao.webposto.adapter.TituloAdapter;
import com.qualityautomacao.webposto.utils.Constantes;
import com.qualityautomacao.webposto.utils.UtilsString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagarTituloActivity extends BaseActivity {

    @BindView(R.id.ptit_rec_titulos) RecyclerView recTitulos;

    private JSONArray dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_titulo);
        super.addToolbar(true);
        ButterKnife.bind(this);

        try {
            dados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO)).getJSONArray("DAD");
        } catch (JSONException e) {
            dados = new JSONArray();
        }
        recTitulos.setLayoutManager(new LinearLayoutManager(this));
        recTitulos.addItemDecoration(new SeparadorLista(ContextCompat.getDrawable(this, R.drawable.separador_lista), dados.length()));
        recTitulos.setAdapter(new TituloAdapter(this, dados));

        Log.i("WEB_POSTO_LOG", "onCreate: " + getIntent().getStringExtra(Constantes.EXTRA_DADO));
    }
}
