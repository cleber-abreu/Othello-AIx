package com.qualityautomacao.webposto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author Wiliam
 * @since 16/01/2017
 */
public class DadoTotalAdapter extends RecyclerView.Adapter<DadoTotalAdapter.Holder>{

    private Context context;
    private JSONArray dados;
    private ProviderHolderAdapter providerHolderAdapter;
    private LayoutInflater inflater;

    private static final int CARD_DADO = 0;
    private static final int CARD_TOTAL = 1;

    public DadoTotalAdapter(Context context, JSONArray dados, ProviderHolderAdapter providerHolderAdapter){
        this.context = context;
        this.dados = dados;
        this.providerHolderAdapter = providerHolderAdapter;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case CARD_DADO: return providerHolderAdapter.getDadoHolder(parent);
            case CARD_TOTAL: return providerHolderAdapter.getTotalHolder(parent);
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

    public interface ProviderHolderAdapter{
        Holder getDadoHolder(ViewGroup parent);
        Holder getTotalHolder(ViewGroup parent);
    }

    public abstract static class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
        }

        public abstract void popular(JSONArray dado, int position) throws JSONException;
    }
}
