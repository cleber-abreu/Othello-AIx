package com.qualityautomacao.webposto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.UtilsString;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wiliam
 * @since 13/01/2017
 */
public class TituloAdapter extends RecyclerView.Adapter<TituloAdapter.Holder> {

    private Context context;
    private LayoutInflater inflater;
    private JSONArray dados;

    private static final int CARD_DADO = 0;
    private static final int CARD_TOTAL = 1;

    public TituloAdapter(Context context, JSONArray dados){
        this.context = context;
        this.dados = dados;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case CARD_DADO: return new DadoHolder(inflater.inflate(R.layout.row_titulo, parent, false));
            case CARD_TOTAL: return new TotalHolder(inflater.inflate(R.layout.row_conta, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        try{
            holder.popular(dados, position);
        }catch (Exception e){
            Toast.makeText(context, "ALGO DEU ERRADO", Toast.LENGTH_SHORT).show();      //TODO
        }
    }

    @Override
    public int getItemCount() {
        return dados.length() == 0 ? 0 : dados.length() + 1;
    }

    @Override
    public int getItemViewType(int position) {
       return position < dados.length() ? CARD_DADO : CARD_TOTAL;
    }

    abstract class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract void popular(JSONArray dado, int position) throws JSONException;
    }

    class DadoHolder extends Holder{
        @BindView(R.id.rtit_txt_titulo) TextView txtTitulo;
        @BindView(R.id.rtit_txt_fornecedor) TextView txtFornecedor;
        @BindView(R.id.rtit_txt_vencimento) TextView txtVencimento;
        @BindView(R.id.rtit_txt_valor) TextView txtValor;

        public DadoHolder(View itemView) {
            super(itemView);
        }

        @Override
        void popular(JSONArray dados, int position) throws JSONException {
            itemView.setBackgroundColor(context.getResources().getColor(position % 2 == 0 ? R.color.bg_row_um : R.color.bg_row_dois));
            JSONArray dado = dados.getJSONArray(position);
            txtTitulo.setText(dado.getString(0));
            txtFornecedor.setText(dado.getString(1));
            txtVencimento.setText(dado.getString(2));
            txtValor.setText(UtilsString.formatarMonetario(dado.optDouble(3, 0)));
        }
    }

    class TotalHolder extends Holder{
        @BindView(R.id.rcon_txt_cliente) TextView txtTotal;
        @BindView(R.id.rcon_txt_valor) TextView txtValor;

        public TotalHolder(View itemView) {
            super(itemView);
        }

        @Override
        void popular(JSONArray dado, int position) throws JSONException {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.bg_row_evidencia));
            txtTotal.setText("TOTAL");
            txtValor.setText(UtilsString.formatarMonetario(dado.getJSONArray(0).optDouble(4,0)));
        }
    }
}
