package com.qualityautomacao.webposto.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;

import java.util.List;

public final class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context _context;
    private final List<Notificacao> _notificacoes;

    public ExpandableListAdapter(Context context, List<Notificacao> notificacoes) {
        _context = context;
        _notificacoes = notificacoes;
    }

    @Override
    public Notificacao getChild(int groupPosition, int childPosititon) {
        return _notificacoes.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Notificacao notificacao = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, parent, false);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.textViewNotificacao);
        txtListChild.setText(notificacao.getDetalhe());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Notificacao getGroup(int groupPosition) {
        return _notificacoes.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return _notificacoes.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Notificacao notificacao = getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.textViewTitulo)).setText(notificacao.getTitulo());
        ((TextView) convertView.findViewById(R.id.textViewHora)).setText(notificacao.getData());
        ((CheckBox) convertView.findViewById(R.id.checkBoxNotificacaoLida)).setChecked(notificacao.isLida());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public int searchById(final int id) {
        for (int i = 0; i < _notificacoes.size(); i++)
            if (_notificacoes.get(i).getCodigo() == id)
                return i;

        return -1;
    }
}
