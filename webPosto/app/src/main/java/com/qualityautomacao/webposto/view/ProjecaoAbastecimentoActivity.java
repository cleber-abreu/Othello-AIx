package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsString;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class ProjecaoAbastecimentoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projecao_abastecimento);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        UtilsWeb.requisitar(new Request(this, "PROJECAO")
                            .onCompleteRequest(new Consumer<JSONObject>() {
                                @Override
                                public void accept(JSONObject jsonObject) throws Exception {
                                    carregarDados(jsonObject);
                                }
                            }));
    }

    private void carregarDados(JSONObject jsonObject) {
        final LineChart lineChart = (LineChart) findViewById(R.id.chartProjecaoAbastecimento);

        try {
            lineChart.setHighlightEnabled(true);
            lineChart.setTouchEnabled(true);
            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(true);
            lineChart.setDrawGridBackground(false);
            lineChart.getAxisRight().setEnabled(false);
            lineChart.getAxisLeft().setStartAtZero(true);
            lineChart.setDescription("");

            lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    for (LineDataSet dataSet : lineChart.getLineData().getDataSets())
                        dataSet.setDrawValues(false);

                    lineChart.getLineData().getDataSetByIndex(dataSetIndex).setDrawValues(true);
                    lineChart.invalidate();
                }

                @Override
                public void onNothingSelected() {
                    for (LineDataSet dataSet : lineChart.getLineData().getDataSets())
                        dataSet.setDrawValues(false);

                    lineChart.invalidate();
                }
            });

            ArrayList<String> dias = new ArrayList<>();

            for (int i = 0; i < jsonObject.length() - 1; i++) {
                dias.add(jsonObject.getJSONArray("OBJ" + (i + 1)).getJSONObject(0).getString("DIA_SEMANA").substring(0, 5) + "   ");
            }

            ArrayList<LineDataSet> lineDataSets = new ArrayList<>();

            for (int i = 0; i < jsonObject.getJSONArray("OBJ" + (i + 1)).length() - 1; i++) {
                ArrayList<Entry> entradas = new ArrayList<>();

                for (int j = 0; j < jsonObject.length() - 1; j++) {
                    entradas.add(new Entry((float) jsonObject.getJSONArray("OBJ" + (j + 1)).getJSONObject(i).getDouble("QUANTIDADE_LT"), j));
                }

                final LineDataSet set = new LineDataSet(entradas, UtilsString.abreviar(jsonObject.getJSONArray("OBJ1").getJSONObject(i).getString("PRODUTO"), 3));

                final int cor = Color.rgb(new Random().nextInt((256 - 100) + 1) + 100, new Random().nextInt((256 - 100) + 1) + 100, new Random().nextInt((256 - 100) + 1) + 100);
                set.setColor(cor);
                set.setCircleColor(cor);
                set.setLineWidth(3f);
                set.setCircleSize(4f);
                set.setDrawCircleHole(true);
                set.setDrawValues(false);
                set.setHighLightColor(cor);
                set.setCircleColorHole(Color.WHITE);
                set.setValueTextSize(12);

                lineDataSets.add(set);
            }

            LineData lineData = new LineData(dias, lineDataSets);
            lineChart.setData(lineData);

            lineChart.invalidate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
