package com.qualityautomacao.webposto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wiliam
 * @since 09/01/2017
 */
public class ContaReceberAdapter extends RecyclerView.Adapter<ContaReceberAdapter.ContaReceberHolder> {

    private Context context;
    private JSONArray dados;
    private ContaReceberDelegate delegate;

    public ContaReceberAdapter(Context context, JSONArray dados, ContaReceberDelegate delegate) {
        this.context = context;
        this.dados = dados;
        this.delegate = delegate;
    }

    @Override
    public ContaReceberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContaReceberHolder(LayoutInflater.from(context).inflate(R.layout.row_simple_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ContaReceberHolder holder, int position) {
        try {
            final JSONObject dado = dados.getJSONObject(position);

            holder.itemView.setBackgroundColor(context.getResources().getColor(position % 2 == 0 ? R.color.bg_light : R.color.bg_dark));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.onClick(dado);
                }
            });
            holder.txtCliente.setText(dado.getString("CLIENTE"));
            holder.txtValor.setText(dado.getString("VALOR"));
        } catch (JSONException e) {
            Log.e("WEB_POSTO_LOG", "onBindViewHolder: ", e);
        }
    }

    @Override
    public int getItemCount() {
        return dados.length();
    }

    public interface ContaReceberDelegate{
        void onClick(JSONObject dado);
    }

    class ContaReceberHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rsim_txt_chave) TextView txtCliente;
        @BindView(R.id.rsim_txt_valor) TextView txtValor;

        public ContaReceberHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
