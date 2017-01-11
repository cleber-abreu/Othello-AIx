package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONException;
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
        UtilsWeb.requisitar(new Request(this, "CONSULTAR_ALTERA_PRECO", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                try {
                    ((ListView) findViewById(R.id.listaCombustivel)).setAdapter(new RowTrocaPrecoAdapter(TrocaPrecoActivity.this, jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void enviarTrocaDePreco() {
        startActivityForResult(new Intent(this, TrocaPrecoEnviarActivity.class).putExtra("TROCAS", getProdutosAlterados()), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
            finish();
    }

    private String getProdutosAlterados() {
        JSONArray json = new JSONArray();

        try {
            final ListView listView = (ListView) findViewById(R.id.listaCombustivel);

            for (int i = 0; i < listView.getCount(); i++) {
                final View child = listView.getChildAt(i);
                final String novoPreco = ((EditText) child.findViewById(R.id.editTextPrecoNovo)).getText().toString();

                if (novoPreco.isEmpty())
                    continue;

                json.put(new JSONObject()
                        .put("APP_CD_PRODUTO", Integer.parseInt(((TextView) child.findViewById(R.id.textViewProdutoId)).getText().toString()))
                        .put("APP_VL_PRECO", Double.parseDouble(novoPreco)));
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return json.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_troca_preco, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_troca_preco) {
            enviarTrocaDePreco();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
