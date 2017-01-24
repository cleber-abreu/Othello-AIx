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
 * Created by wiliam on 20/01/17.
 */

public class TituloAdapter extends RecyclerView.Adapter<TituloAdapter.Holder>{

    private final JSONObject pacoteDados;
    private final LayoutInflater inflater;
    private final int qtTitulos;

    private static final int TYPE_TITULO = 1;
    private static final int TYPE_TOTAL = 2;

    private static final int INDEX_CLIENTE = 0;
    private static final int INDEX_VALOR_DOCUMENTO = 1;
    private static final int INDEX_SALDO = 2;
    private static final int INDEX_DATA = 3;


    private static final String INDEX_TOTAL_SALDO = "TOTAL_SALDO";
    private static final String INDEX_TOTAL_DOCUMENTO = "TOTAL_DOCUMENTO";

    public TituloAdapter(Context context, JSONObject pacoteDados){
        this.pacoteDados = pacoteDados;
        this.qtTitulos = pacoteDados.optJSONArray("DAD").length();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TITULO: return new TituloHolder(inflater.inflate(R.layout.row_receber_detalhado, parent, false));
            default: return new TotalHolder(inflater.inflate(R.layout.row_total_receber_detalhado, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.popula(pacoteDados, position);
    }

    @Override
    public int getItemCount() {
        return qtTitulos + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == qtTitulos) return TYPE_TOTAL;
        else return TYPE_TITULO;
    }

    static abstract class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
        }

        public abstract void popula(JSONObject jsonObject, int position);
    }

    class TituloHolder extends Holder{
        @BindView(R.id.rdtr_txt_cliente) TextView txtCliente;
        @BindView(R.id.rdtr_txt_total) TextView txtTotal;
        @BindView(R.id.rdtr_txt_pago) TextView txtPago;
        @BindView(R.id.rdtr_txt_a_receber) TextView txtAReceber;

        public TituloHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void popula(JSONObject jsonObject, int position) {
            itemView.setBackgroundColor(itemView.getResources().getColor(position % 2 == 0 ? R.color.bg_row_um : R.color.bg_row_dois));

            JSONArray dado = pacoteDados.optJSONArray("DAD").optJSONArray(position);
            double total = dado.optDouble(INDEX_VALOR_DOCUMENTO, 0);
            double aReceber = dado.optDouble(INDEX_SALDO, 0);
            txtCliente.setText(dado.optString(INDEX_CLIENTE));
            txtTotal.setText(UtilsString.formatarMonetario(total));
            txtAReceber.setText(UtilsString.formatarMonetario(aReceber));
            txtPago.setText(UtilsString.formatarMonetario(total - aReceber));
        }
    }

    class TotalHolder extends Holder{
        @BindView(R.id.rtrd_txt_total_documentos) TextView txtTotalDocumento;
        @BindView(R.id.rtrd_txt_total_a_receber) TextView txtTotalAReceber;

        public TotalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void popula(JSONObject jsonObject, int position) {
            txtTotalDocumento.setText(UtilsString.formatarMonetario(jsonObject.optDouble(INDEX_TOTAL_DOCUMENTO,0)));
            txtTotalAReceber.setText(UtilsString.formatarMonetario(jsonObject.optDouble(INDEX_TOTAL_SALDO,0)));
        }
    }
}