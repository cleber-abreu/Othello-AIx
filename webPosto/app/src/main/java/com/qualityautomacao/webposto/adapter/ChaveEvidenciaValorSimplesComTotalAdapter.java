package com.qualityautomacao.webposto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wiliam
 * @since 12/01/2017
 */
public class ChaveEvidenciaValorSimplesComTotalAdapter extends RecyclerView.Adapter<ChaveEvidenciaValorSimplesComTotalAdapter.Holder>{

    private Context context;
    private ChaveValorTotal dadosDelegate;
    private ClickDelegate clickDelegate;
    private LayoutInflater inflater;

    public ChaveEvidenciaValorSimplesComTotalAdapter(Context context, ChaveValorTotal dadosDelegate, ClickDelegate clickDelegate){
        this.context = context;
        this.dadosDelegate = dadosDelegate;
        this.clickDelegate = clickDelegate;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.row_conta, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if(dadosDelegate.isTotal(position)){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.acentuada));
            holder.txtLabel.setText(dadosDelegate.labelTotal());
            holder.txtValor.setText(dadosDelegate.valorTotal());
        }else{
            holder.itemView.setBackgroundColor(context.getResources().getColor(position % 2 == 0 ? R.color.bg_light : R.color.bg_dark));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) { clickDelegate.onClick(position); }
            });
            holder.txtLabel.setText(dadosDelegate.labelDado(position));
            holder.txtValor.setText(dadosDelegate.valorDado(position));
        }
    }

    @Override
    public int getItemCount() {
        return dadosDelegate.getSize();
    }

    public interface ClickDelegate {
        void onClick(int position);
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.rcon_txt_cliente) TextView txtLabel;
        @BindView(R.id.rcon_txt_valor) TextView txtValor;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
