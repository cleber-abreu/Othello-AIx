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
import com.qualityautomacao.webposto.view.ReceberChequeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.qualityautomacao.webposto.utils.UtilsWeb.UW_SHOW_PROGRESS_DIALOG;
import static com.qualityautomacao.webposto.utils.UtilsWeb.UW_SHOW_TOAST_ON_EXCEPTION;

/**
 * Created by wiliam on 06/01/17.
 */

public class FiltroReceberCheque implements FiltroPresenter {
    private FiltroActivity activity;

    public FiltroReceberCheque(FiltroActivity activity){
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
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("DATA_INI", dados.getDataInicio());
                requestParams.put("DATA_FIM", dados.getDataFim());
                requestParams.put("FL_DATA", dados.getBomparaMovimento());
                requestParams.put("FL_RECEBIDO", dados.getEstadoContaAR());

            } catch (JSONException e) {
                Log.e("WEB_POSTO_LOG", "consulta: ", e);
            }

            UtilsWeb.requisitar(
                    new Request(activity, "DETALHE_CHEQUE_RECEBER")
                            .setDados(requestParams.toString())
                            .setFlags(UW_SHOW_PROGRESS_DIALOG | UW_SHOW_TOAST_ON_EXCEPTION)
                            .onCompleteRequest(new Consumer<JSONObject>() {
                                @Override
                                public void accept(JSONObject jsonObject) throws Exception {
                                    Intent intent = new Intent(activity, ReceberChequeActivity.class);
                                    intent.putExtra(Constantes.EXTRA_DADO, jsonObject.toString());
                                    activity.startActivity(intent);
                                }
                            })
            );
        }else{
            Toast.makeText(activity, "Intervalo invalido", Toast.LENGTH_SHORT).show();  // TODO COLOCAR POR RECURSO
        }
    }
}
