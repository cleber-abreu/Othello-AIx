package com.qualityautomacao.webposto.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_CONSUMER = "ARG_CONSUMER";
    public static final String ARG_FRAG = "ARG_FRAG";

    public ConsumerUnchecked<View> acao;
    private int mPage;
    private int fragmento;

    public static PageFragment newInstance(int page, int fragmento, ConsumerUnchecked<View> acao) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putSerializable(ARG_CONSUMER, acao);
        args.putInt(ARG_FRAG, fragmento);

        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        acao = (ConsumerUnchecked<View>) getArguments().getSerializable(ARG_CONSUMER);
        fragmento = getArguments().getInt(ARG_FRAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(fragmento, container, false);
        acao.accept(view);

        return view;
    }
}