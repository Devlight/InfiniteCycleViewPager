package com.gigamole.infinitecycleviewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by GIGAMOLE on 8/4/16.
 */

// Custom scroller for setting own snap duration and interpolator
class InfiniteCycleScroller extends Scroller {

    private int mDuration;

    public InfiniteCycleScroller(final Context context) {
        super(context);
    }

    public InfiniteCycleScroller(final Context context, final Interpolator interpolator) {
        super(context, interpolator);
    }

    public InfiniteCycleScroller(final Context context, final Interpolator interpolator, final boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setDuration(final int duration) {
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
