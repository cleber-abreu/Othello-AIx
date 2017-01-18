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
import com.qualityautomacao.webposto.utils.Constantes;
import com.qualityautomacao.webposto.utils.UtilsString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceberTituloActivity extends BaseActivity {

    @BindView(R.id.rect_rec_titulos) RecyclerView recTitulos;

    private static final int INDEX_CLIENTE = 0;
    private static final int INDEX_VALOR = 1;
    private static final int INDEX_TOTAL = 3;

    private JSONArray dados;
    private static LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_titulo);
        super.addToolbar(true);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);

        try {
            dados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO)).getJSONArray("DAD");
        } catch (JSONException e) {
            dados = new JSONArray();
        }

        recTitulos.setLayoutManager(new LinearLayoutManager(this));
        recTitulos.addItemDecoration(new SeparadorLista(ContextCompat.getDrawable(this, R.drawable.separador_lista), dados.length()));
        recTitulos.setAdapter(new DadoTotalAdapter(this, dados, getProviderHolderAdapter()));

        Log.i("WEB_POSTO_LOG", "onCreate: " + getIntent().getStringExtra(Constantes.EXTRA_DADO));
    }

    private DadoTotalAdapter.ProviderHolderAdapter getProviderHolderAdapter() {
        return new DadoTotalAdapter.ProviderHolderAdapter() {
            @Override
            public DadoTotalAdapter.Holder getDadoHolder(ViewGroup parent) {
                return new DadoHolder(inflater.inflate(R.layout.row_simple_item, parent, false));
            }

            @Override
            public DadoTotalAdapter.Holder getTotalHolder(ViewGroup parent) {
                return new TotalHolder(inflater.inflate(R.layout.row_total_simples, parent, false));
            }
        };
    }

    class DadoHolder extends DadoTotalAdapter.Holder {
        @BindView(R.id.rsim_txt_chave) TextView txtCliente;
        @BindView(R.id.rsim_txt_valor) TextView txtValor;

        public DadoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void popular(JSONArray dados, int position) throws JSONException {
            itemView.setBackgroundColor(getResources().getColor(position % 2 == 0 ? R.color.bg_row_um : R.color.bg_row_dois));
            JSONArray dado = dados.getJSONArray(position);
            txtCliente.setText(dado.getString(INDEX_CLIENTE));
            txtValor.setText(UtilsString.formatarMonetario(dado.optDouble(INDEX_VALOR, 0)));
        }
    }

    class TotalHolder extends DadoTotalAdapter.Holder {
        @BindView(R.id.rtot_txt_valor) TextView txtValor;

        public TotalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void popular(JSONArray dado, int position) throws JSONException {
            txtValor.setText(UtilsString.formatarMonetario(dado.getJSONArray(0).optDouble(INDEX_TOTAL,0)));
        }
    }
}
