package com.qualityautomacao.webposto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.UtilsString;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wiliam on 23/01/17.
 */

public class ReceberChequeAdapter extends RecyclerView.Adapter<ReceberChequeAdapter.ReceberChequeHolder>{

    private final JSONObject pacoteDados;
    private final LayoutInflater inflater;
    private final boolean exibeData;
    private final int qtElementos;

    private static final int TYPE_ELEMENTO = 1;
    private static final int TYPE_TOTAL = 2;

    private static final int INDEX_CLIENTE = 0;
    private static final int INDEX_VALOR = 1;
    private static final int INDEX_DATA = 2;
    private static final String INDEX_TOTAL = "TOTAL";

    public ReceberChequeAdapter(Context context, JSONObject pacoteDados){
        this.pacoteDados = pacoteDados;
        this.qtElementos = pacoteDados.optJSONArray("DAD").length();
        this.exibeData =  pacoteDados.optJSONArray("DAD").optJSONArray(0).optBoolean(INDEX_DATA, true);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ReceberChequeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ELEMENTO: return new ElementoHolder(inflater.inflate(R.layout.row_cliente_data_valor, parent, false));
            default: return new TotalHolder(inflater.inflate(R.layout.row_total_simples, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ReceberChequeHolder holder, int position) {
        holder.popula(pacoteDados, position);
    }

    @Override
    public int getItemCount() {
        return qtElementos + 1;
    }

    abstract static class ReceberChequeHolder extends RecyclerView.ViewHolder{

        public ReceberChequeHolder(View itemView) {
            super(itemView);
        }

        public abstract void popula(JSONObject jsonObject, int position);
    }

    class ElementoHolder extends ReceberChequeHolder{
        @BindView(R.id.rcdv_txt_cliente) TextView txtCliente;
        @BindView(R.id.rcdv_txt_valor) TextView txtValor;
        @BindView(R.id.rcdv_txt_data) TextView txtData;

        public ElementoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void popula(JSONObject pacoteDados, int position) {
            itemView.setBackgroundColor(itemView.getResources().getColor(position % 2 == 0 ? R.color.bg_row_um : R.color.bg_row_dois));

            JSONArray dado = pacoteDados.optJSONArray("DAD").optJSONArray(position);
            txtCliente.setText(dado.optString(INDEX_CLIENTE));
            txtValor.setText(UtilsString.formatarMonetario(dado.optDouble(INDEX_VALOR, 0)));
            if(exibeData) txtData.setText(dado.optString(INDEX_DATA));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == qtElementos) return TYPE_TOTAL;
        else return TYPE_ELEMENTO;
    }

    class TotalHolder extends ReceberChequeHolder{
        @BindView(R.id.rtot_txt_valor) TextView txtTotal;

        public TotalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void popula(JSONObject jsonObject, int position) {
            txtTotal.setText(UtilsString.formatarMonetario(jsonObject.optDouble(INDEX_TOTAL, 0)));
        }
    }
}
