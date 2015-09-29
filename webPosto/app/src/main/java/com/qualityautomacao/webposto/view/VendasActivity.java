package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.ConsumerUnchecked;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.TabAction;
import com.qualityautomacao.webposto.utils.UtilsInterface;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

public class VendasActivity extends FragmentActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        UtilsInterface.setAbas(this, R.layout.fragment_venda, new TabAction(this, R.string.title_tab_venda_hoje, new ConsumerUnchecked<View>() {
            @Override
            public void accept(View view) {
                carregarDados(view, 0);
            }
        }), new TabAction(this, R.string.title_tab_venda_5, new ConsumerUnchecked<View>() {
            @Override
            public void accept(View view) {
                carregarDados(view, 1);
            }
        }), new TabAction(this, R.string.title_tab_venda_15, new ConsumerUnchecked<View>() {
            @Override
            public void accept(View view) {
                carregarDados(view, 2);
            }
        }), new TabAction(this, R.string.title_tab_venda_30, new ConsumerUnchecked<View>() {
            @Override
            public void accept(View view) {
                carregarDados(view, 3);
            }
        }));
    }

    private void carregarDados(final View view, final int dias) {
        UtilsWeb.requisitar(new Request(this, "RELATORIO_VENDA")
                .setDados("{\"DATA\" : " + dias + "}")
                .setFlags(0)
                .onCompleteRequest(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        ((ListView) view).setAdapter(new RowVendasAdapter(VendasActivity.this, jsonObject));
                    }
                })
                .onPreExecute(new Runnable() {
                    @Override
                    public void run() {
                        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_vendas);

                        if (viewPager.getCurrentItem() != dias)
                            return;

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