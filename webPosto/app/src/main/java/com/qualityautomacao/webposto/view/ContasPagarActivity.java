package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qualityautomacao.webposto.utils.UtilsWeb.UW_SHOW_PROGRESS_DIALOG;

public class ContasPagarActivity extends AppCompatActivity {

    @BindView(R.id.conpag_txt_diario)   TextView txtDiario;
    @BindView(R.id.conpag_txt_cinco)    TextView txtCinco;
    @BindView(R.id.conpag_txt_quinze)   TextView txtQuinze;
    @BindView(R.id.conpag_txt_trinta)   TextView txtTrinta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas_pagar);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UtilsWeb.requisitar(new Request(this, "RODAPE_CONTAS_A_PAGAR")
                .setFlags(UW_SHOW_PROGRESS_DIALOG)
                .onCompleteRequest(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        if(jsonObject.optInt("RET", 1) < 1){
                            JSONObject dados = jsonObject.optJSONObject("RES");

                            txtDiario.setText(dados.optString("DIARIO"));
                            txtCinco.setText(dados.optString("CINCO"));
                            txtQuinze.setText(dados.optString("QUINZE"));
                            txtTrinta.setText(dados.optString("TRINTA"));
                        }
                    }
                }));
    }

    @OnClick(R.id.conpag_btn_titulo)
    public void onBtnTituloClick(View v){
        startActivity(new Intent(this, FiltroActivity.class));
    }
}
