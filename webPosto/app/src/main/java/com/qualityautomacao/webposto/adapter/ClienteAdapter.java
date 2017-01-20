package com.qualityautomacao.webposto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wiliam on 20/01/17.
 */

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteHolder>{

    private static final int INDEX_NOME = 1;

    private JSONArray dados;
    private LayoutInflater inflater;
    private OnClickClienteListener clickDelegate;

    public ClienteAdapter(Context context, JSONArray dados, OnClickClienteListener onClickClienteListener){
        this.dados = dados;
        this.clickDelegate = onClickClienteListener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ClienteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClienteHolder(inflater.inflate(R.layout.row_label, parent, false));
    }

    @Override
    public void onBindViewHolder(ClienteHolder holder, int position) {
        try {

            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(position % 2 == 0 ? R.color.bg_row_um : R.color.bg_row_dois));
            final JSONArray dado = dados.getJSONArray(position);
            holder.txtCliente.setText(dado.getString(INDEX_NOME));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickDelegate.onClick(dado);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dados.length();
    }


    public interface OnClickClienteListener{
        void onClick(JSONArray dado);
    }

    class ClienteHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.rlbl_txt_label) TextView txtCliente;

        public ClienteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
