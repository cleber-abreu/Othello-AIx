package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificacoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);

        if (getIntent().hasExtra("TOKEN"))
            UtilsWeb.token = (Token) getIntent().getSerializableExtra("TOKEN");

        ((ExpandableListView) findViewById(R.id.expandableListViewNotificacoes)).setOnGroupExpandListener(marcarNotificacaoComoLida());

        UtilsWeb.requisitar(this, "DETALHE_NOTIFICACOES", "{}", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) throws Exception {
                ((ExpandableListView) findViewById(R.id.expandableListViewNotificacoes))
                        .setAdapter(new ExpandableListAdapter(NotificacoesActivity.this, getNotificacoes(jsonObject)));
            }
        }, -1, null, new Runnable() {
            @Override
            public void run() {
                if (!getIntent().hasExtra("TOKEN"))
                    return;

                final int posicao = getItemNotificacao(getIntent().getIntExtra("ID", 0));
                final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewNotificacoes);

                expandableListView.expandGroup(posicao);
                expandableListView.setSelectedGroup(posicao);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notificacoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Notificacao> getNotificacoes(final JSONObject jsonObject) {
        List<Notificacao> notificacoes = new ArrayList<>();
        try {
            final JSONArray obj = jsonObject.getJSONArray("OBJ");

            for (int i = 0; i < obj.length(); i++) {
                JSONObject dados = obj.getJSONObject(i);
                notificacoes.add(new Notificacao(dados.getString("TITULO"),
                                                 dados.getString("DETALHE"),
                                                 dados.getString("DATA"),
                                                 dados.getInt("CODIGO"),
                                                 dados.getString("LIDA").equals("S")));
            }
        } catch (JSONException e) {
            System.out.println(e);
        }

        return notificacoes;
    }

    private int getItemNotificacao(final int id) {
        return ((ExpandableListAdapter) ((ExpandableListView) findViewById(R.id.expandableListViewNotificacoes)).getExpandableListAdapter()).searchById(id);
    }

    public ExpandableListView.OnGroupExpandListener marcarNotificacaoComoLida() {
        return new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                final ExpandableListAdapter expandableListAdapter = (ExpandableListAdapter) ((ExpandableListView) findViewById(R.id.expandableListViewNotificacoes)).getExpandableListAdapter();
                expandableListAdapter.getGroup(groupPosition).setLida(true);

                UtilsWeb.requisitar(NotificacoesActivity.this,
                                    "LER_NOTIFICACAO",
                                    "{\"NFI_CD_NOTIFICACAO\": " + expandableListAdapter.getGroup(groupPosition).getCodigo() + "}",
                                    0);
            }
        };
    }
}
