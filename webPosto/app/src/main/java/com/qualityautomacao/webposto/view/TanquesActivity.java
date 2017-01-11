package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONException;
import org.json.JSONObject;

public class TanquesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanques);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    public void carregarDados() {
        UtilsWeb.requisitar(new Request(this, "TANQUE", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                try {
                    ((ListView) findViewById(R.id.listaTanques)).setAdapter(new RowTanquesAdapter(TanquesActivity.this, jsonObject));
                } catch (JSONException e) {
                    Log.e("WEB_POSTO_LOG", "accept: ", e);
                }
            }
        }));
    }
}
