package com.qualityautomacao.webposto.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT;
    private final TabAction abas[];
    private final Context context;
    private final int fragmento;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context, int fragmento, TabAction... abas) {
        super(fm);
        this.context = context;
        this.abas = abas;
        PAGE_COUNT = abas.length;
        this.fragmento = fragmento;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1, fragmento, abas[position].acao);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return abas[position].descricao;
    }
}
