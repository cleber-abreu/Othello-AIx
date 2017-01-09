package com.qualityautomacao.webposto.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.adapter.ContaReceberAdapter;
import com.qualityautomacao.webposto.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceberTituloActivity extends AppCompatActivity {
    @BindView(R.id.rect_rec_contas) RecyclerView recContas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_titulo);
        ButterKnife.bind(this);

        try {
            JSONObject json = new JSONObject(getIntent().getStringExtra(Constantes.EXTRA_DADO));
            ContaReceberAdapter adapter = new ContaReceberAdapter(this, json.getJSONArray("OBJ"), new ContaReceberAdapter.ContaReceberDelegate() {
                @Override
                public void onClick(JSONObject dado) {
                    try {
                        Toast.makeText(ReceberTituloActivity.this, dado.get("CLIENTE") + " " + dado.get("VALOR"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e("WEB_POSTO_LOG", "onClick: ", e);
                    }
                }
            });
            recContas.setLayoutManager(new LinearLayoutManager(this));
            recContas.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("WEB_POSTO_LOG", "onCreate: ", e);
        }

        Log.i("WEB_POSTO_LOG", "onCreate: " + getIntent().getStringExtra(Constantes.EXTRA_DADO));
    }
}
