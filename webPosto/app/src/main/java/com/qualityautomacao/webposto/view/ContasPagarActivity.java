package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContasPagarActivity extends BaseActivity {

    @BindView(R.id.conpag_txt_diario)   TextView txtDiario;
    @BindView(R.id.conpag_txt_cinco)    TextView txtCinco;
    @BindView(R.id.conpag_txt_quinze)   TextView txtQuinze;
    @BindView(R.id.conpag_txt_trinta)   TextView txtTrinta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas_pagar);
        ButterKnife.bind(this);

        showLoadDialog();
        UtilsWeb.requisitar(new Request(this, "RODAPE_CONTAS_A_PAGAR", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                JSONObject dados = jsonObject.optJSONObject("RES");

                txtDiario.setText(dados.optString("DIARIO"));
                txtCinco.setText(dados.optString("CINCO"));
                txtQuinze.setText(dados.optString("QUINZE"));
                txtTrinta.setText(dados.optString("TRINTA"));

                hideLoadDialog();
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) {
                hideLoadDialog();
                Toast.makeText(ContasPagarActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.conpag_btn_titulo)
    public void onClickBtnTitulo(View v){
        Intent intent = new Intent(this, FiltroActivity.class);
        intent.putExtra(FiltroActivity.EXTRA_DESTINO, FiltroActivity.DESTINO_PAGAR_TITULOS);
        startActivity(intent);
    }

    @OnClick(R.id.conpag_btn_cheque)
    public void onClickBtnCheque(View v){
        Intent intent = new Intent(this, FiltroActivity.class);
        intent.putExtra(FiltroActivity.EXTRA_DESTINO, FiltroActivity.DESTINO_PAGAR_CHEQUE);
        startActivity(intent);
    }


}
