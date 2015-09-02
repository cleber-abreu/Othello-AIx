package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

public class TrocaPrecoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_preco);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    private void carregarDados() {
        UtilsWeb.requisitar(this, "CONSULTAR_ALTERA_PRECO", "{}", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) throws Exception {
                ((ListView) findViewById(R.id.listaCombustivel)).setAdapter(new RowTrocaPrecoAdapter(TrocaPrecoActivity.this, jsonObject));
            }
        });
    }
}
