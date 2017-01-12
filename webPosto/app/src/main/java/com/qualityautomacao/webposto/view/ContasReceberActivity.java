package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsString;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContasReceberActivity extends BaseActivity {

    @BindView(R.id.conrec_txt_diario)   TextView txtDiario;
    @BindView(R.id.conrec_txt_cinco)    TextView txtCinco;
    @BindView(R.id.conrec_txt_quinze)   TextView txtQuinze;
    @BindView(R.id.conrec_txt_trinta)   TextView txtTrinta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas_receber);
        ButterKnife.bind(this);

        showLoadDialog();
        UtilsWeb.requisitar(new Request(this, "MOBILE", "RODAPE_CONTAS_A_RECEBER", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                JSONObject dados = jsonObject.optJSONObject("OBJ");

                txtDiario.setText(UtilsString.formatarMonetario(dados.optDouble("DIARIO", 0)));
                txtCinco.setText(UtilsString.formatarMonetario(dados.optDouble("CINCO", 0)));
                txtQuinze.setText(UtilsString.formatarMonetario(dados.optDouble("QUINZE", 0)));
                txtTrinta.setText(UtilsString.formatarMonetario(dados.optDouble("TRINTA", 0)));

                hideLoadDialog();
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) {
                hideLoadDialog();
                Toast.makeText(ContasReceberActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @OnClick(R.id.conrec_btn_titulo)
    public void onClickBtnTitulo(View v){
        Intent intent = new Intent(this, FiltroActivity.class);
        intent.putExtra(FiltroActivity.EXTRA_DESTINO, FiltroActivity.DESTINO_RECEBER_TITULO);
        startActivity(intent);
    }

    @OnClick(R.id.conrec_btn_cheque)
    public void onClickBtnCheque(View v){
        Intent intent = new Intent(this, FiltroActivity.class);
        intent.putExtra(FiltroActivity.EXTRA_DESTINO, FiltroActivity.DESTINO_RECEBER_CHEQUE);
        startActivity(intent);
    }

    @OnClick(R.id.conrec_btn_carta_frete)
    public void onClickBtnCartaFrete(View v){
        Intent intent = new Intent(this, FiltroActivity.class);
        intent.putExtra(FiltroActivity.EXTRA_DESTINO, FiltroActivity.DESTINO_RECEBER_CARTA_FRETE);
        startActivity(intent);
    }

    @OnClick(R.id.conrec_btn_cartao)
    public void onClickBtnCartao(View v){
        Intent intent = new Intent(this, FiltroActivity.class);
        intent.putExtra(FiltroActivity.EXTRA_DESTINO, FiltroActivity.DESTINO_RECEBER_CARTAO);
        startActivity(intent);
    }
}
