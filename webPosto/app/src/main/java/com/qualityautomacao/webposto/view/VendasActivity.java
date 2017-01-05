package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsInterface;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VendasActivity extends Activity {
    private ProgressBar progressBar;
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
        UtilsWeb.requisitar(new Request(this, "RELATORIO_VENDA_ANDROID")
                .setDados(String.format("{\"DATA_INICIO\" : \"%s\",\"DATA_FIM\" : \"%s\"}", dtIni, dtFim))
                .setFlags(0)
                .onCompleteRequest(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        ((ListView) findViewById(R.id.listaVendas)).setAdapter(new RowVendasAdapter(VendasActivity.this, jsonObject));
                    }
                })
                .onPreExecute(new Runnable() {
                    @Override
                    public void run() {

                        if (progressBar != null)
                            progressBar.setVisibility(View.VISIBLE);
                        else {
                            progressBar = new ProgressBar(VendasActivity.this);
                            addContentView(progressBar, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        }
                    }
                })
                .onPosExecute(new Runnable() {
                    @Override
                    public void run() {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                    }
                }));
    }
}