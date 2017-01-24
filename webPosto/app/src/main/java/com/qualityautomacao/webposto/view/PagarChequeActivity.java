package com.qualityautomacao.webposto.view;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
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

public class PagarChequeActivity extends BaseActivity {

    @BindView(R.id.pche_rec_titulos) RecyclerView recCheques;

    private static final int INDEX_CHEQUE = 0;
    private static final int INDEX_BOM_PARA = 1;
    private static final int INDEX_VALOR = 2;
    private static final int INDEX_CONTA = 3;
    private static final int INDEX_TOTAL = 4;

    private JSONArray dados;

    private static LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_cheque);

        super.addToolbar(true);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);

        try {
            dados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO)).getJSONArray("DAD");
        } catch (JSONException e) {
            dados = new JSONArray();
        }
        recCheques.setLayoutManager(new LinearLayoutManager(this));
        recCheques.addItemDecoration(new SeparadorLista(ContextCompat.getDrawable(this, R.drawable.separador_lista), dados.length()));
        recCheques.setAdapter(new DadoTotalAdapter(this, dados, getProviderHolderAdapter()));

        Log.i("WEB_POSTO_LOG", "onCreate: " + getIntent().getStringExtra(Constantes.EXTRA_DADO));
    }

    private DadoTotalAdapter.ProviderHolderAdapter getProviderHolderAdapter() {
        return new DadoTotalAdapter.ProviderHolderAdapter() {
            @Override
            public DadoTotalAdapter.Holder getDadoHolder(ViewGroup parent) {
                return new DadoHolder(inflater.inflate(R.layout.row_pagar_cheque, parent, false));
            }

            @Override
            public DadoTotalAdapter.Holder getTotalHolder(ViewGroup parent) {
                return new TotalHolder(inflater.inflate(R.layout.row_total_simples, parent, false));
            }
        };
    }

    class DadoHolder extends DadoTotalAdapter.Holder {
        @BindView(R.id.rpch_txt_cheque) TextView txtCheque;
        @BindView(R.id.rpch_txt_bom_para) TextView txtBomPara;
        @BindView(R.id.rpch_txt_valor) TextView txtValor;
        @BindView(R.id.rpch_txt_conta) TextView txtConta;

        public DadoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void popular(JSONArray dados, int position) throws JSONException {
            itemView.setBackgroundColor(getResources().getColor(position % 2 == 0 ? R.color.bg_row_um : R.color.bg_row_dois));
            JSONArray dado = dados.getJSONArray(position);
            txtCheque.setText(dado.getString(INDEX_CHEQUE));
            txtBomPara.setText(dado.getString(INDEX_BOM_PARA));
            txtValor.setText(UtilsString.formatarMonetario(dado.optDouble(INDEX_VALOR, 0)));
            txtConta.setText(dado.getString(INDEX_CONTA));
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
