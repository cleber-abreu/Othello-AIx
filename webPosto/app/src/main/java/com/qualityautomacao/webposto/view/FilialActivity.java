package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONObject;

public class FilialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filial);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            final LinearLayout listView = (LinearLayout) findViewById(R.id.linearLayoutFiliais);

            if (listView.getChildCount() > 0)
                return;

            final JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("DADOS"));
            final JSONArray unidades = jsonObject.getJSONArray("DAD");
            final JSONArray obj = jsonObject.getJSONArray("OBJ");

            final int size = unidades.length();

            for (int i = 0; i < size; i++) {
                final Button button = new Button(this);
                button.setText(unidades.getJSONArray(i).getString(1));
                button.setMinHeight(150);

                final int finalI = i;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            final JSONArray jsonArray = unidades.getJSONArray(finalI);

                            UtilsWeb.token = new Token(
                                    jsonArray.getInt(0),
                                    jsonArray.getString(1),
                                    obj.getJSONObject(1).getInt("USU_CD_USUARIO"),
                                    obj.getJSONObject(2).getInt("PER_CD_PERFIL"),
                                    obj.getJSONObject(0).getInt("RED_CD_REDE"));

                            UtilsWeb.verificarLiberacaoDispositivo(FilialActivity.this, new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(FilialActivity.this, MainActivity.class));
                                }
                            });
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                });

                listView.addView(button);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}