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
import com.qualityautomacao.webposto.view.ListaClienteActivity;
import com.qualityautomacao.webposto.view.ReceberChequeActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
        if(!UtilsDate.intervaloValido(dados.getDataInicio(), dados.getDataFim(), UtilsDate.dd_MM_yyyy)){
            activity.showMessage(R.string.intervalo_invalido);
        }else if(dados.isPorCliente()){
            consultaClientes(dados);
        }else{
            consultaNormal(dados);
        }
    }

    private void consultaClientes(final DadosFiltro dados) {
        activity.showLoadDialog();
        JSONObject requestParams = new JSONObject();
        try {
            requestParams.put("TIPO_TITULO", "H");
        } catch (JSONException e) {
            Log.e("WEB_POSTO_LOG", "consulta: ", e);
        }

        UtilsWeb.requisitar(new Request(activity, "MOBILE", "COMBOBOX_CLIENTE", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                Intent intent = new Intent(activity, ListaClienteActivity.class);
                intent.putExtra(Constantes.EXTRA_DADO, jsonObject.toString());
                intent.putExtra(Constantes.EXTRA_DADO_FILTRO, dados);
                intent.putExtra(Constantes.EXTRA_TIPO_CONSULTA, Constantes.TIPO_CONSULTA_CHEQUE);
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
    }

    public void consultaNormal(DadosFiltro dados) {
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

        UtilsWeb.requisitar(new Request(activity, "MOBILE", "DETALHE_CHEQUE_RECEBER", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                    Intent intent = new Intent(activity, ReceberChequeActivity.class);
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
            }
        ).setDados(requestParams.toString()));
    }
}