package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.ConsumerUnchecked;
import com.qualityautomacao.webposto.utils.TabAction;
import com.qualityautomacao.webposto.utils.UtilsInterface;

public class VendasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        UtilsInterface.setAbas(this, R.layout.fragment_page, new TabAction("1", new ConsumerUnchecked<View>() {
            @Override
            public void accept(View view) {
                ((TextView) view).setText(view.toString());
            }
        }), new TabAction("2", new ConsumerUnchecked<View>() {
            @Override
            public void accept(View view) {
                ((TextView) view).setText("Bosta em Gás");
            }
        }));

//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), this));

        // Give the TabLayout the ViewPager
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);
    }
}
