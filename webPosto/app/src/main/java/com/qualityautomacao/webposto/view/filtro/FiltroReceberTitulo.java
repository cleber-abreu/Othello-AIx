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
import com.qualityautomacao.webposto.view.ReceberTituloActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wiliam on 06/01/17.
 */

public class FiltroReceberTitulo implements FiltroPresenter {
    private FiltroActivity activity;

    public FiltroReceberTitulo(FiltroActivity activity){
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
                requestParams.put("DATA_INI", dados.getDataInicio());
                requestParams.put("DATA_FIM", dados.getDataFim());
                requestParams.put("FL_DATA", dados.getBomparaMovimento());
                requestParams.put("FL_RECEBIDO", dados.getEstadoContaAR());

            } catch (JSONException e) {
                Log.e("WEB_POSTO_LOG", "consulta: ", e);
            }

            UtilsWeb.requisitar(new Request(activity, "MOBILE", "DETALHE_TITULO_RECEBER", new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) {
                        Intent intent = new Intent(activity, ReceberTituloActivity.class);
                        intent.putExtra(Constantes.EXTRA_DADO, jsonObject.toString());
                        activity.hideLoadDialog();
                        activity.startActivity(intent);
                    }
                }, new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        activity.hideLoadDialog();
                        activity.showMessage(s);
                    }
                }).setDados(requestParams.toString()));
        }else{
            Toast.makeText(activity, "Intervalo invalido", Toast.LENGTH_SHORT).show();  // TODO COLOCAR POR RECURSO
            activity.showMessage(R.string.intervalo_invalido);
        }
    }
}
