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
import com.qualityautomacao.webposto.view.PagarTituloActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wiliam on 05/01/17.
 */
public class FiltroPagarTitulo implements FiltroPresenter {

    private FiltroActivity activity;

    public FiltroPagarTitulo(FiltroActivity activity){
        this.activity = activity;
    }

    @Override
    public void carregaComponentes() {
        activity.initDatePicker(
                UtilsDate.getHoje(UtilsDate.dd_MM_yyyy),
                UtilsDate.getDataIncMes(UtilsDate.dd_MM_yyyy, 1));
        activity.initEstadoConta();
    }

    @Override
    public void consulta(DadosFiltro dados) {
        if(UtilsDate.intervaloValido(dados.getDataInicio(), dados.getDataFim(), UtilsDate.dd_MM_yyyy)){
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("DATA_INICIAL", dados.getDataInicio());
                requestParams.put("DATA_FINAL", dados.getDataFim());
                requestParams.put("SITUACAO", dados.getEstadoConta());
            } catch (JSONException e) {
                Log.e("WEB_POSTO_LOG", "consulta: ", e);
            }

            activity.showLoadDialog();
            UtilsWeb.requisitar(new Request(activity, "MOBILE", "DETALHE_TITULO_PAGAR", new Consumer<JSONObject>() {
                @Override
                public void accept(JSONObject jsonObject) {
                    Intent intent = new Intent(activity, PagarTituloActivity.class);
                    intent.putExtra(Constantes.EXTRA_DADO, jsonObject.toString());
                    activity.startActivity(intent);
//                    activity.finish();        TODO CONFIRMAR NECESSIDADE
                    activity.hideLoadDialog();
                }
            }, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                    activity.hideLoadDialog();
                }
            }).setDados(requestParams.toString()));
        }else{
            Toast.makeText(activity, "Intervalo invalido", Toast.LENGTH_SHORT).show();  // TODO COLOCAR POR RECURSO
        }
    }
}
