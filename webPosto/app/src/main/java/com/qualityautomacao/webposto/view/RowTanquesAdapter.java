package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RowTanquesAdapter extends ArrayAdapter<String> {
    private final JSONArray list;
    private LayoutInflater layoutInflater;

    public RowTanquesAdapter(Activity context, JSONObject retorno) throws JSONException {
        super(context, R.layout.row_tanques);
        this.list = retorno.getJSONArray("OBJ");
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.row_tanques, parent, false);
            holder.capacidade = (TextView) convertView.findViewById(R.id.capacidade);
            holder.produto = (TextView) convertView.findViewById(R.id.produto);
            holder.data = (TextView) convertView.findViewById(R.id.data);
            holder.volume = (TextView) convertView.findViewById(R.id.volume);
            holder.progress = (ProgressBar) convertView.findViewById(R.id.barraProgresso);

            convertView.setTag(holder);

            JSONObject item = list.getJSONObject(position);
            holder.produto.setText(item.getString("PRODUTO"));
            holder.capacidade.setText(item.getString("CAPACIDADE"));
            holder.volume.setText(item.getString("VOLUME"));
            holder.data.setText(item.getString("DATA"));

            holder.progress.setMax(100);
            holder.progress.setSecondaryProgress(0);
            holder.progress.setProgress((int) (item.optDouble("PERCENTUAL", 0) * 100));

        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView capacidade;
        TextView produto;
        TextView data;
        TextView volume;
        ProgressBar progress;
    }
}
