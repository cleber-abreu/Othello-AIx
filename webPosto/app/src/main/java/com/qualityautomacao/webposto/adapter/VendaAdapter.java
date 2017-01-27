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
 * Created by wiliam on 26/01/17.
 */

public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.VendaHolder>{

    private LayoutInflater inflater;
    private JSONObject jsonDados;
    private final int qtElementos;


    private static final int TYPE_ELEMENTO = 1;
    private static final int TYPE_TOTAL = 2;
    private static final int TYPE_MEDIA = 3;

    private static final int INDEX_PRODUTO = 0;
    private static final int INDEX_VALOR = 1;
    private static final int INDEX_QUANTIDADE = 2;
    private static final int INDEX_EH_COMBUSTIVEL = 3;
    private static final String INDEX_TOTAL_VALOR = "TOTAL_VALOR";
    private static final String INDEX_TOTAL_QUANTIDADE = "TOTAL_QUANTIDADE";
    private static final String INDEX_MEDIA_VALOR = "MEDIA_VALOR";
    private static final String INDEX_MEDIA_QUANTIDADE = "MEDIA_QUANTIDADE";

    public VendaAdapter(Context ctx, JSONObject jsonDados){
        this.inflater = LayoutInflater.from(ctx);
        this.qtElementos = jsonDados.optJSONArray("DAD").length();
        this.jsonDados = jsonDados;
    }

    @Override
    public VendaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ELEMENTO: return new ProdutoVendaHolder(inflater.inflate(R.layout.row_vendas, parent, false));
            case TYPE_TOTAL: return new TotalVendaHolder(inflater.inflate(R.layout.row_rodape_vendas, parent, false));
            default: return new MediaVendaHolder(inflater.inflate(R.layout.row_rodape_vendas, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(VendaHolder holder, int position) {
        holder.popula(jsonDados, position);
    }

    @Override
    public int getItemCount() {
        return qtElementos + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == qtElementos) return TYPE_TOTAL;
        else if(position == qtElementos + 1) return TYPE_MEDIA;
        else return TYPE_ELEMENTO;
    }

    abstract static class VendaHolder extends RecyclerView.ViewHolder{

        VendaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        abstract void popula(JSONObject jsonObject, int position);
    }

    class ProdutoVendaHolder extends VendaHolder{
        @BindView(R.id.rven_txt_produto) TextView txtProduto;
        @BindView(R.id.rven_txt_quantidade) TextView txtQuantidade;
        @BindView(R.id.rven_txt_valor) TextView txtValor;
        @BindView(R.id.rven_lbl_unidade) TextView lblUnidade;

        ProdutoVendaHolder(View itemView) {
            super(itemView);
        }

        @Override
        void popula(JSONObject jsonObject, int position) {
            itemView.setBackgroundColor(itemView.getResources().getColor(position % 2 == 0 ? R.color.bg_row_um : R.color.bg_row_dois));

            JSONArray dado = jsonObject.optJSONArray("DAD").optJSONArray(position);
            txtProduto.setText(dado.optString(INDEX_PRODUTO));
            txtQuantidade.setText(dado.optString(INDEX_QUANTIDADE));
            txtValor.setText(UtilsString.formatarMonetario(dado.optDouble(INDEX_VALOR, 0)));
            lblUnidade.setText(dado.optBoolean(INDEX_EH_COMBUSTIVEL) ? "Lts:" : "Qt:");
        }
    }

    class TotalVendaHolder extends VendaHolder{
        @BindView(R.id.rrv_txt_titulo) TextView txtTitulo;
        @BindView(R.id.rrv_txt_quantidade) TextView txtQuantidade;
        @BindView(R.id.rrv_txt_valor) TextView txtValor;

        TotalVendaHolder(View itemView) {
            super(itemView);
        }

        @Override
        void popula(JSONObject jsonObject, int position) {
            txtTitulo.setText("Total");
            txtValor.setText(UtilsString.formatarMonetario(jsonObject.optDouble(INDEX_TOTAL_VALOR, 0)));
            txtQuantidade.setText(jsonObject.optString(INDEX_TOTAL_QUANTIDADE));
        }
    }

    class MediaVendaHolder extends VendaHolder{
        @BindView(R.id.rrv_txt_titulo) TextView txtTitulo;
        @BindView(R.id.rrv_txt_quantidade) TextView txtQuantidade;
        @BindView(R.id.rrv_txt_valor) TextView txtValor;

        MediaVendaHolder(View itemView) {
            super(itemView);
        }

        @Override
        void popula(JSONObject jsonObject, int position) {
            txtTitulo.setText("Média");
            txtValor.setText(UtilsString.formatarMonetario(jsonObject.optDouble(INDEX_MEDIA_VALOR, 0)));
            txtQuantidade.setText(jsonObject.optString(INDEX_MEDIA_QUANTIDADE));
        }
    }
}
