package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.ClienteAdapter;
import com.qualityautomacao.webposto.adapter.SeparadorLista;
import com.qualityautomacao.webposto.model.DadosFiltro;
import com.qualityautomacao.webposto.utils.Constantes;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaClienteActivity extends BaseActivity {

    @BindView(R.id.lcli_rec_clientes) RecyclerView recClientes;

    private JSONArray dados;
    private String tipoConsulta;
    private DadosFiltro dadosFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);
        super.addToolbar(true);
        ButterKnife.bind(this);

        try {
            tipoConsulta = getIntent().getStringExtra(Constantes.EXTRA_TIPO_CONSULTA);
            dadosFiltro = (DadosFiltro) getIntent().getSerializableExtra(Constantes.EXTRA_DADO_FILTRO);
            dados = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO)).getJSONArray("DAD");
        } catch (JSONException e) {
            dados = new JSONArray();
        }

        recClientes.setLayoutManager(new LinearLayoutManager(this));
        recClientes.setAdapter(new ClienteAdapter(this, dados, new ClienteAdapter.OnClickClienteListener() {
            @Override
            public void onClick(JSONArray dado) {
                irParaDetalhes(dado);
            }
        }));
    }

    private void irParaDetalhes(JSONArray dado) {
        showLoadDialog();

        UtilsWeb.requisitar(new Request(this, "MOBILE", getFuncao(), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                Intent intent = new Intent(ListaClienteActivity.this, ReceberTituloActivity.class);
                intent.putExtra(Constantes.EXTRA_DADO, jsonObject.toString());
                hideLoadDialog();
                startActivity(intent);
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) {
                hideLoadDialog();
                showMessage(s);
            }
        }).setDados(getParams(dado).toString()));
    }

    private JSONObject getParams(JSONArray dado){
        JSONObject requestParams = new JSONObject();

        try {
            switch (tipoConsulta){
                case Constantes.TIPO_CONSULTA_TITULO:
                case Constantes.TIPO_CONSULTA_CHEQUE:
                case Constantes.TIPO_CONSULTA_CARTA:
                    requestParams.put("CD_CLIENTE", dado.getInt(0));
                    requestParams.put("DATA_INICIAL", dadosFiltro.getDataInicio());
                    requestParams.put("DATA_FINAL", dadosFiltro.getDataFim());
                    requestParams.put("FL_DATA", dadosFiltro.getBomparaMovimento());
                    requestParams.put("SITUACAO", dadosFiltro.getEstadoContaAR());
                    break;
                case Constantes.TIPO_CONSULTA_CARTAO:
                    requestParams.put("CD_ADMINISTRADORA", dado.getInt(0));
                    requestParams.put("DATA_INICIAL", dadosFiltro.getDataInicio());
                    requestParams.put("DATA_FINAL", dadosFiltro.getDataFim());
                    requestParams.put("FL_DATA", dadosFiltro.getBomparaMovimento());
                    requestParams.put("SITUACAO", dadosFiltro.getEstadoContaAR());
                    break;
            }

        } catch (JSONException e) {
            Log.e("WEB_POSTO_LOG", "consulta: ", e);
        }

        return requestParams;
    }

    private String getFuncao(){

        switch (tipoConsulta){
            case Constantes.TIPO_CONSULTA_TITULO: return "DETALHE_TITULO_RECEBER";
            case Constantes.TIPO_CONSULTA_CHEQUE: return "DETALHE_CHEQUE_RECEBER";
            case Constantes.TIPO_CONSULTA_CARTA: return "DETALHE_CARTA_FRETE_RECEBER";
            case Constantes.TIPO_CONSULTA_CARTAO: return "DETALHE_CARTAO";
            default: return "";
        }
    }
}
