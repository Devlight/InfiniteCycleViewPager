package com.gigamole.sample.screens;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.gigamole.sample.R;
import com.gigamole.sample.adapters.MainPagerAdapter;
import com.stanko.tools.DeviceInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeviceInfo.init(this);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);

        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setTitles("HORIZONTAL", "VERTICAL", "TWO-WAY");
        navigationTabStrip.setViewPager(viewPager);
    }
}
