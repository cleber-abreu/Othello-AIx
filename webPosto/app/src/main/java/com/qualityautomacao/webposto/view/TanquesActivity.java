package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

public class TanquesActivity extends AppCompatActivity {

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
        UtilsWeb.requisitar(this, "TANQUE", "{}", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) throws Exception {
                ((ListView) findViewById(R.id.listaTanques)).setAdapter(new RowTanquesAdapter(TanquesActivity.this, jsonObject));
            }
        });
    }
}
