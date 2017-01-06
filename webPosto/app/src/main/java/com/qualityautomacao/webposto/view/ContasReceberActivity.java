package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qualityautomacao.webposto.utils.UtilsWeb.UW_SHOW_PROGRESS_DIALOG;
import static com.qualityautomacao.webposto.utils.UtilsWeb.UW_SHOW_TOAST_ON_EXCEPTION;

public class ContasReceberActivity extends Activity {

    @BindView(R.id.conrec_txt_diario)   TextView txtDiario;
    @BindView(R.id.conrec_txt_cinco)    TextView txtCinco;
    @BindView(R.id.conrec_txt_quinze)   TextView txtQuinze;
    @BindView(R.id.conrec_txt_trinta)   TextView txtTrinta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas_receber);
        ButterKnife.bind(this);

        UtilsWeb.requisitar(new Request(this, "RODAPE_CONTAS_A_RECEBER")
                .setFlags(UW_SHOW_PROGRESS_DIALOG | UW_SHOW_TOAST_ON_EXCEPTION)
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
}
