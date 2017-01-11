package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsInterface;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONException;
import org.json.JSONObject;

public class VendasActivity extends BaseActivity {
    private EditText dtIni;
    private EditText dtFim;
    private ImageButton buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        dtIni = ((EditText) findViewById(R.id.dtInicioEditText));
        dtIni.setFocusable(false);
        dtIni.setText(UtilsInterface.setSevenDaysBefore());
        UtilsInterface.setDateCalendario(dtIni);

        dtFim = ((EditText) findViewById(R.id.dtFimEditText));
        dtFim.setFocusable(false);
        dtFim.setText(UtilsInterface.setDate());
        UtilsInterface.setDateCalendario(dtFim);

        buscar = ((ImageButton) findViewById(R.id.buscarButton));
        buscar.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View view) {
                                          try {
                                              carregarDados(view, dtIni.getText().toString(), dtFim.getText().toString());
                                          } catch (Exception e) {
                                              e.printStackTrace();
                                          }
                                      }
                                  }
        );
    }

    private void carregarDados(final View view, final String dtIni, final String dtFim) {
        showLoadDialog();
        UtilsWeb.requisitar(new Request(this, "RELATORIO_VENDA_ANDROID", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                try {
                    ((ListView) findViewById(R.id.listaVendas)).setAdapter(new RowVendasAdapter(VendasActivity.this, jsonObject));
                    hideLoadDialog();
                } catch (JSONException e) {
                    Log.e("WEB_POSTO_LOG", "accept: ", e);
                }
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) {
                hideLoadDialog();
            }
        }).setDados(String.format("{\"DATA_INICIO\" : \"%s\",\"DATA_FIM\" : \"%s\"}", dtIni, dtFim)));
    }
}