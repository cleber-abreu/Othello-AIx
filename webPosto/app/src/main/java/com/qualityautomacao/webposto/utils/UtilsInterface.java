package com.qualityautomacao.webposto.utils;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.qualityautomacao.webposto.R;

public abstract class UtilsInterface {

    public static void setAbas(FragmentActivity activity, int fragmento, TabAction... acoes) {
        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(activity.getSupportFragmentManager(), activity, fragmento, acoes));

        TabLayout tabLayout = (TabLayout) activity.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
