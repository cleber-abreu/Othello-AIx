package com.qualityautomacao.webposto.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.ChaveEvidenciaValorSimplesComTotalAdapter;
import com.qualityautomacao.webposto.adapter.ChaveValorTotal;
import com.qualityautomacao.webposto.utils.Constantes;
import com.qualityautomacao.webposto.utils.UtilsString;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagarTituloActivity extends AppCompatActivity {

    @BindView(R.id.ptit_rec_titulos) RecyclerView recTitulos;

    private JSONObject dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_titulo);
        ButterKnife.bind(this);

        try {
            dados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO));
        } catch (JSONException e) {
            dados = new JSONObject();
        }
        recTitulos.setLayoutManager(new LinearLayoutManager(this));

        recTitulos.setAdapter(new ChaveEvidenciaValorSimplesComTotalAdapter(this, getDadosDelegate(), getClickDelegate()));
        Log.i("WEB_POSTO_LOG", "onCreate: " + getIntent().getStringExtra(Constantes.EXTRA_DADO));
    }

    private ChaveValorTotal getDadosDelegate() {
        return new ChaveValorTotal() {
            @Override
            public int getSize() {
                return dados.length() + 1;
            }

            @Override
            public boolean isTotal(int position) {
                return position >= dados.length();
            }

            @Override
            public String labelTotal() {
                return "TOTAL: ";
            }

            @Override
            public String valorTotal() {
                try {
                    return UtilsString.formatarMonetario(dados.getJSONObject("RES").optDouble("TOTAL", 0));
                }catch (Exception e){
                    return UtilsString.formatarMonetario(0);
                }
            }

            @Override
            public String labelDado(int position) {
                try {
                    return dados.getJSONArray("OBJ").getJSONObject(position).getString("TITULO");
                }catch (Exception e){
                    return "---";
                }
            }

            @Override
            public String valorDado(int position) {
                try {
                    return UtilsString.formatarMonetario(dados.getJSONArray("OBJ").getJSONObject(position).optDouble("VALOR", 0));
                }catch (Exception e){
                    return UtilsString.formatarMonetario(0);
                }
            }
        };
    }

    private ChaveEvidenciaValorSimplesComTotalAdapter.ClickDelegate getClickDelegate() {
        return new ChaveEvidenciaValorSimplesComTotalAdapter.ClickDelegate() {
            @Override
            public void onClick(int position) {
                try{
                    Toast.makeText(PagarTituloActivity.this, dados.getJSONArray("OBJ").getJSONObject(position).toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(PagarTituloActivity.this, "ALGO ERRADO", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
