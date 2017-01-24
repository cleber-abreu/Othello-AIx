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

    private JSONObject dados;

    private static final String STATE_DADOS = "dados";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas_receber);
        ButterKnife.bind(this);

        if(savedInstanceState == null){
            requisitarDados();
        }else{
            try{
                dados = new JSONObject(savedInstanceState.getString(STATE_DADOS));
            }catch (Exception e){
                dados = new JSONObject();
                showMessage(R.string.dados_invalidos);
            }
            carregaDados();
        }
    }

    private void carregaDados() {
        txtDiario.setText(UtilsString.formatarMonetario(dados.optDouble("DIARIO", 0)));
        txtCinco.setText(UtilsString.formatarMonetario(dados.optDouble("CINCO", 0)));
        txtQuinze.setText(UtilsString.formatarMonetario(dados.optDouble("QUINZE", 0)));
        txtTrinta.setText(UtilsString.formatarMonetario(dados.optDouble("TRINTA", 0)));
    }

    private void requisitarDados(){
        showLoadDialog();
        UtilsWeb.requisitar(new Request(this, "MOBILE", "RODAPE_CONTAS_A_RECEBER", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                dados = jsonObject.optJSONObject("OBJ");
                carregaDados();
                hideLoadDialog();
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) {
                hideLoadDialog();
                showMessage(s);
            }
        }));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_DADOS, dados.toString());
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
