package com.gigamole.sample.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gigamole.sample.screens.HorizontalPagerFragment;
import com.gigamole.sample.screens.TwoWayPagerFragment;
import com.gigamole.sample.screens.VerticalPagerFragment;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final static int COUNT = 3;

    private final static int HORIZONTAL = 0;
    private final static int VERTICAL = 1;
    private final static int TWO_WAY = 2;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case VERTICAL:
                return new VerticalPagerFragment();
            case TWO_WAY:
                return new TwoWayPagerFragment();
            case HORIZONTAL:
            default:
                return new HorizontalPagerFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
