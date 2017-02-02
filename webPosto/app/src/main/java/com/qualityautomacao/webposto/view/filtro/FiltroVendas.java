package com.qualityautomacao.webposto.view.filtro;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.qualityautomacao.webposto.model.DadosFiltro;
import com.qualityautomacao.webposto.utils.Constantes;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsDate;
import com.qualityautomacao.webposto.utils.UtilsWeb;
import com.qualityautomacao.webposto.view.FiltroActivity;
import com.qualityautomacao.webposto.view.VendasActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by wiliam on 26/01/17.
 */

public class FiltroVendas implements FiltroPresenter {

    FiltroActivity activity;

    public FiltroVendas(FiltroActivity activity){
        this.activity = activity;
    }

    @Override
    public void carregaComponentes() {
        activity.initDatePicker(
                UtilsDate.getDataIncDia(UtilsDate.dd_MM_yyyy, -7),
                UtilsDate.getHoje(UtilsDate.dd_MM_yyyy), new Date().getTime());
    }

    @Override
    public void consulta(DadosFiltro dados) {
        if(UtilsDate.intervaloValido(dados.getDataInicio(), dados.getDataFim(), UtilsDate.dd_MM_yyyy)){
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("DATA_INICIAL", dados.getDataInicio());
                requestParams.put("DATA_FINAL", dados.getDataFim());

            } catch (JSONException e) {
                Log.e("WEB_POSTO_LOG", "consulta: ", e);
            }

            activity.showLoadDialog();
            UtilsWeb.requisitar(new Request(activity, "MOBILE", "RELATORIO_VENDA", new Consumer<JSONObject>() {
                @Override
                public void accept(JSONObject jsonObject) {
                    Intent intent = new Intent(activity, VendasActivity.class);
                    intent.putExtra(Constantes.EXTRA_DADO, jsonObject.toString());
                    activity.startActivity(intent);
                    activity.hideLoadDialog();
                }
            }, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    activity.hideLoadDialog();
                    Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                }
            }).setDados(requestParams.toString()));
        }else{
            Toast.makeText(activity, "Intervalo inválido", Toast.LENGTH_SHORT).show();  // TODO COLOCAR POR RECURSO
        }
    }
}
