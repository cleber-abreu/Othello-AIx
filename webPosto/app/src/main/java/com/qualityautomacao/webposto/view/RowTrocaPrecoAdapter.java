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
        super(context, R.layout.row_vendas);

        this.list = jsonObject.getJSONArray("OBJ");

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.length() + 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertViewRetorno = layoutInflater.inflate(R.layout.row_troca_preco, null);

        try {
            final JSONObject jsonObject = list.optJSONObject(position);
            ViewHolder holder = new ViewHolder();

            holder.valorNovo = (EditText) convertViewRetorno.findViewById(R.id.editTextPrecoNovo);
            holder.descricaoProduto = (TextView) convertViewRetorno.findViewById(R.id.textViewProduto);
            holder.valorAtual = (TextView) convertViewRetorno.findViewById(R.id.textViewPrecoAtual);

            convertViewRetorno.setTag(holder);

            if (jsonObject != null) {
                holder.descricaoProduto.setText(jsonObject.getString("PRO_DS_PRODUTO"));
                holder.valorAtual.setText("R$ " + jsonObject.getString("APP_VL_PRECO"));
                holder.produto = jsonObject.getInt("APP_CD_PRODUTO");
            } else {
                holder.valorNovo.setVisibility(View.INVISIBLE);
                holder.descricaoProduto.setVisibility(View.INVISIBLE);
                holder.valorAtual.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
        return convertViewRetorno;
    }

    static class ViewHolder {
        EditText valorNovo;
        TextView valorAtual;
        TextView descricaoProduto;
        int produto;
    }
}
