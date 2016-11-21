package com.gigamole.infinitecycleviewpager;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by GIGAMOLE on 7/27/16.
 */

// PagerAdapter that wrap original ViewPager adapter with infinite scroll feature.
// There is VIRTUAL_ITEM_COUNT which equals to 10_000_000.
// At start, ViewPager set position to half of virtual and find nearest zero position.
public abstract class InfiniteCyclePagerAdapter extends PagerAdapter {

    private final static int VIRTUAL_ITEM_COUNT = 10_000_000;

    private OnNotifyDataSetChangedListener mOnNotifyDataSetChangedListener;

    public PagerAdapter getPagerAdapter() {
        return this;
    }

    public void setOnNotifyDataSetChangedListener(final OnNotifyDataSetChangedListener onNotifyDataSetChangedListener) {
        mOnNotifyDataSetChangedListener = onNotifyDataSetChangedListener;
    }

    public abstract int getItemCount();

    public abstract Object instantiateItemView(ViewGroup container, int position);

    public abstract void destroyItemView(ViewGroup container, int position, Object object);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        destroyItemView(container, getVirtualPosition(position), object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public int getCount() {
        if (getItemCount() == 0) return 0;
        return VIRTUAL_ITEM_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(getVirtualPosition(position));
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(getVirtualPosition(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return instantiateItemView(container, getVirtualPosition(position));
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        // Callback for invalidating transformer position
        if (mOnNotifyDataSetChangedListener != null) mOnNotifyDataSetChangedListener.onChanged();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    // Main feature of this adapter which return virtual position
    // relative to virtual count and original count
    protected int getVirtualPosition(final int realPosition) {
        return realPosition % getItemCount();
    }

    protected interface OnNotifyDataSetChangedListener {
        void onChanged();
    }
}