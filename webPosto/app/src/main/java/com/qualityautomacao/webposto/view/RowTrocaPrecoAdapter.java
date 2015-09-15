package com.qualityautomacao.webposto.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RowTrocaPrecoAdapter extends ArrayAdapter<String> {

    private final JSONArray list;
    private LayoutInflater layoutInflater;

    public RowTrocaPrecoAdapter(Activity context, JSONObject jsonObject) throws JSONException {
        super(context, R.layout.row_troca_preco);

        this.list = jsonObject.getJSONArray("OBJ");

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            final JSONObject jsonObject = list.optJSONObject(position);
            final ViewHolder holder;

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.row_troca_preco, null);
                holder = new ViewHolder();

                holder.valorNovo = (EditText) convertView.findViewById(R.id.editTextPrecoNovo);
                holder.descricaoProduto = (TextView) convertView.findViewById(R.id.textViewProduto);
                holder.valorAtual = (TextView) convertView.findViewById(R.id.textViewPrecoAtual);
                holder.produto = (TextView) convertView.findViewById(R.id.textViewProdutoId);

                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            holder.descricaoProduto.setText(jsonObject.getString("PRO_DS_PRODUTO"));
            holder.valorAtual.setText("R$ " + jsonObject.getString("APP_VL_PRECO"));
            holder.produto.setText(jsonObject.getString("APP_CD_PRODUTO"));
        } catch (Exception e) {
            System.err.println(e);
        }
        return convertView;
    }

    static class ViewHolder {
        EditText valorNovo;
        TextView valorAtual;
        TextView descricaoProduto;
        TextView produto;
    }
}
