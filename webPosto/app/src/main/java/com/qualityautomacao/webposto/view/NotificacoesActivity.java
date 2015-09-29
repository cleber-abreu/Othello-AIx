package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificacoesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);

        if (getIntent().hasExtra("TOKEN"))
            UtilsWeb.token = (Token) getIntent().getSerializableExtra("TOKEN");

        ((ExpandableListView) findViewById(R.id.expandableListViewNotificacoes)).setOnGroupExpandListener(marcarNotificacaoComoLida());

        UtilsWeb.requisitar(new Request(this, "DETALHE_NOTIFICACOES")
                .onCompleteRequest(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        ((ExpandableListView) findViewById(R.id.expandableListViewNotificacoes))
                                .setAdapter(new ExpandableListAdapter(NotificacoesActivity.this, getNotificacoes(jsonObject)));
                    }
                })
                .onPosExecute(new Runnable() {
                    @Override
                    public void run() {
                        if (!getIntent().hasExtra("TOKEN"))
                            return;

                        final int posicao = getItemNotificacao(getIntent().getIntExtra("ID", 0));
                        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewNotificacoes);

                        expandableListView.expandGroup(posicao);
                        expandableListView.setSelectedGroup(posicao);
                    }
                }));
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

                UtilsWeb.requisitar(new Request(NotificacoesActivity.this, "LER_NOTIFICACAO")
                                    .setDados("{\"NFI_CD_NOTIFICACAO\": " + expandableListAdapter.getGroup(groupPosition).getCodigo() + "}")
                                    .setFlags(0));
            }
        };
    }
}
