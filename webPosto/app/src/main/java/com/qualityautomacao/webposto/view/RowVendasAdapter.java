package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RowVendasAdapter extends ArrayAdapter<String> {

    private final JSONArray list;
    private final JSONObject rodape;
    private LayoutInflater layoutInflater;

    public RowVendasAdapter(Activity context, JSONObject jsonObject) throws JSONException {
        super(context, R.layout.row_vendas);

        this.list = jsonObject.getJSONArray("OBJ");
        this.rodape = jsonObject.getJSONObject("RES");

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.length() + 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        try {
            holder = new ViewHolder();

            if (position >= list.length()) {
                convertView = layoutInflater.inflate(R.layout.row_rodape_vendas, parent, false);
            }else{
                convertView = layoutInflater.inflate(R.layout.row_vendas, parent, false);
            }

            holder.quantidade = (TextView) convertView.findViewById(R.id.volume);
            holder.produto = (TextView) convertView.findViewById(R.id.produto);
            holder.valor = (TextView) convertView.findViewById(R.id.valor);
            convertView.setTag(holder);

            if (position == list.length()) {
                holder.produto.setText(this.rodape.optString("PRODUTO",""));
                holder.valor.setText(this.rodape.optString("VALOR",""));
                holder.quantidade.setText(this.rodape.optString("QUANTIDADE",""));
            } else if (position == list.length() + 1) {
                holder.produto.setText("Total:");
                holder.valor.setText(this.rodape.optString("SUMVALOR",""));
                holder.quantidade.setText(this.rodape.optString("SUMQUANTIDADE",""));
            } else {
                JSONObject item = list.getJSONObject(position);
                holder.produto.setText(item.getString("PRODUTO"));
                holder.valor.setText(item.getString("VALOR"));
                holder.quantidade.setText(item.getString("QUANTIDADE"));
            }

        } catch (JSONException e) {
            Log.e("WEB_POSTO_LOG", "getView: ", e);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView quantidade;
        TextView valor;
        TextView produto;
    }
}
