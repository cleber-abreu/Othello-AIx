package com.qualityautomacao.webposto.view.filtro;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.DadosFiltro;
import com.qualityautomacao.webposto.utils.Constantes;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsDate;
import com.qualityautomacao.webposto.utils.UtilsWeb;
import com.qualityautomacao.webposto.view.FiltroActivity;
import com.qualityautomacao.webposto.view.ReceberCartaoActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wiliam on 06/01/17.
 */

public class FiltroReceberCartao implements FiltroPresenter {
    private FiltroActivity activity;

    public FiltroReceberCartao(FiltroActivity activity){
        this.activity = activity;
    }

    @Override
    public void carregaComponentes() {
        activity.initBomparaMovimento();
        activity.initDatePicker(
                UtilsDate.getHoje(UtilsDate.dd_MM_yyyy),
                UtilsDate.getDataIncMes(UtilsDate.dd_MM_yyyy, 1));
        activity.initEstadoContaAR();
        activity.initCliente();
    }

    @Override
    public void consulta(DadosFiltro dados) {
        if(UtilsDate.intervaloValido(dados.getDataInicio(), dados.getDataFim(), UtilsDate.dd_MM_yyyy)){
            activity.showLoadDialog();
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("DATA_INICIAL", dados.getDataInicio());
                requestParams.put("DATA_FINAL", dados.getDataFim());
                requestParams.put("FL_DATA", dados.getBomparaMovimento());
                requestParams.put("SITUACAO", dados.getEstadoContaAR());

            } catch (JSONException e) {
                Log.e("WEB_POSTO_LOG", "consulta: ", e);
            }

            UtilsWeb.requisitar(new Request(activity, "MOBILE", "DETALHE_CARTAO", new Consumer<JSONObject>() {
                @Override
                public void accept(JSONObject jsonObject) {
                        Intent intent = new Intent(activity, ReceberCartaoActivity.class);
                        intent.putExtra(Constantes.EXTRA_DADO, jsonObject.toString());
                        activity.startActivity(intent);
                        activity.hideLoadDialog();
                    }
                }, new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        activity.hideLoadDialog();
                        activity.showMessage(s);
                    }
                }).setDados(requestParams.toString()));
        }else{
            activity.showMessage(R.string.intervalo_invalido);
        }
    }
}