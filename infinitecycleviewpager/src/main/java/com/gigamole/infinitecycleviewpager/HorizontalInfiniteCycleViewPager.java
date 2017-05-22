package com.gigamole.infinitecycleviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalInfiniteCycleViewPager extends ViewPager implements ViewPageable {

    private InfiniteCycleManager mInfiniteCycleManager;

    public HorizontalInfiniteCycleViewPager(final Context context) {
        super(context);
        init(context, null);
    }

    public HorizontalInfiniteCycleViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attributeSet) {
        mInfiniteCycleManager = new InfiniteCycleManager(context, this, attributeSet);
    }

    public float getMinPageScaleOffset() {
        return mInfiniteCycleManager == null ? 0.0F : mInfiniteCycleManager.getMinPageScaleOffset();
    }

    public void setMinPageScaleOffset(final float minPageScaleOffset) {
        if (mInfiniteCycleManager != null)
            mInfiniteCycleManager.setMinPageScaleOffset(minPageScaleOffset);
    }

    public float getCenterPageScaleOffset() {
        return mInfiniteCycleManager == null ? 0.0F : mInfiniteCycleManager.getCenterPageScaleOffset();
    }

    public void setCenterPageScaleOffset(final float centerPageScaleOffset) {
        if (mInfiniteCycleManager != null)
            mInfiniteCycleManager.setCenterPageScaleOffset(centerPageScaleOffset);
    }

    public float getMinPageScale() {
        return mInfiniteCycleManager == null ? 0.0F : mInfiniteCycleManager.getMinPageScale();
    }

    public void setMinPageScale(final float minPageScale) {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.setMinPageScale(minPageScale);
    }

    public float getMaxPageScale() {
        return mInfiniteCycleManager == null ? 0.0F : mInfiniteCycleManager.getMaxPageScale();
    }

    public void setMaxPageScale(final float maxPageScale) {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.setMaxPageScale(maxPageScale);
    }

    public boolean isMediumScaled() {
        return mInfiniteCycleManager != null && mInfiniteCycleManager.isMediumScaled();
    }

    public void setMediumScaled(final boolean mediumScaled) {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.setMediumScaled(mediumScaled);
    }

    public int getScrollDuration() {
        return mInfiniteCycleManager == null ? 0 : mInfiniteCycleManager.getScrollDuration();
    }

    public void setScrollDuration(final int scrollDuration) {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.setScrollDuration(scrollDuration);
    }

    public int getPageDuration() {
        return mInfiniteCycleManager == null ? 0 : mInfiniteCycleManager.getPageDuration();
    }

    public void setPageDuration(final int pageDuration) {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.setPageDuration(pageDuration);
    }

    public Interpolator getInterpolator() {
        return mInfiniteCycleManager == null ? null : mInfiniteCycleManager.getInterpolator();
    }

    public void setInterpolator(final Interpolator interpolator) {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.setInterpolator(interpolator);
    }

    public boolean isVertical() {
        return mInfiniteCycleManager != null && mInfiniteCycleManager.isVertical();
    }

    public OnInfiniteCyclePageTransformListener getOnInfiniteCyclePageTransformListener() {
        return mInfiniteCycleManager == null ?
                null : mInfiniteCycleManager.getOnInfiniteCyclePageTransformListener();
    }

    public void setOnInfiniteCyclePageTransformListener(
            final OnInfiniteCyclePageTransformListener onInfiniteCyclePageTransformListener
    ) {
        if (mInfiniteCycleManager != null)
            mInfiniteCycleManager.setOnInfiniteCyclePageTransformListener(onInfiniteCyclePageTransformListener);
    }

    @Override
    public void setPageTransformer(final boolean reverseDrawingOrder, final PageTransformer transformer) {
        super.setPageTransformer(
                false, mInfiniteCycleManager == null ?
                        transformer : mInfiniteCycleManager.getInfinityCyclePageTransformer()
        );
    }

    @Override
    protected void setChildrenDrawingOrderEnabled(final boolean enabled) {
        super.setChildrenDrawingOrderEnabled(InfiniteCycleManager.DEFAULT_DISABLE_FLAG);
    }

    @Override
    public void setClipChildren(final boolean clipChildren) {
        super.setClipChildren(InfiniteCycleManager.DEFAULT_DISABLE_FLAG);
    }

    @Override
    public void setDrawingCacheEnabled(final boolean enabled) {
        super.setDrawingCacheEnabled(InfiniteCycleManager.DEFAULT_DISABLE_FLAG);
    }

    @Override
    protected void setChildrenDrawingCacheEnabled(final boolean enabled) {
        super.setChildrenDrawingCacheEnabled(InfiniteCycleManager.DEFAULT_DISABLE_FLAG);
    }

    @Override
    public void setWillNotCacheDrawing(final boolean willNotCacheDrawing) {
        super.setWillNotCacheDrawing(InfiniteCycleManager.DEFAULT_ENABLE_FLAG);
    }

    @Override
    public void setPageMargin(final int marginPixels) {
        super.setPageMargin(InfiniteCycleManager.DEFAULT_PAGE_MARGIN);
    }

    @Override
    public void setOffscreenPageLimit(final int limit) {
        super.setOffscreenPageLimit(InfiniteCycleManager.DEFAULT_OFFSCREEN_PAGE_LIMIT);
    }

    @Override
    public void setOverScrollMode(final int overScrollMode) {
        super.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected boolean addViewInLayout(final View child, final int index, final ViewGroup.LayoutParams params) {
        return super.addViewInLayout(child, 0, params);
    }

    @Override
    public void addView(final View child, final int index, final ViewGroup.LayoutParams params) {
        super.addView(child, 0, params);
    }

    @Override
    public void setAdapter(final PagerAdapter adapter) {
        if (mInfiniteCycleManager == null) super.setAdapter(adapter);
        else {
            super.setAdapter(mInfiniteCycleManager.setAdapter(adapter));
            mInfiniteCycleManager.resetPager();
        }
    }

    @Override
    public PagerAdapter getAdapter() {
        if (mInfiniteCycleManager == null) return super.getAdapter();
        return mInfiniteCycleManager.getInfiniteCyclePagerAdapter() == null ? super.getAdapter() :
                mInfiniteCycleManager.getInfiniteCyclePagerAdapter().getPagerAdapter();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        try {
            return mInfiniteCycleManager == null ? super.onTouchEvent(ev) :
                    mInfiniteCycleManager.onTouchEvent(ev) && super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        try {
            return mInfiniteCycleManager == null ? super.onInterceptTouchEvent(ev) :
                    mInfiniteCycleManager.onInterceptTouchEvent(ev) && super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    @Override
    public void onWindowFocusChanged(final boolean hasWindowFocus) {
        if (mInfiniteCycleManager != null)
            mInfiniteCycleManager.onWindowFocusChanged(hasWindowFocus);
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.stopAutoScroll();
        super.onDetachedFromWindow();
    }

    @Override
    public void setCurrentItem(final int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(final int item, final boolean smoothScroll) {
        if (mInfiniteCycleManager != null)
            super.setCurrentItem(mInfiniteCycleManager.setCurrentItem(item), true);
    }

    public int getRealItem() {
        return mInfiniteCycleManager == null ?
                getCurrentItem() : mInfiniteCycleManager.getRealItem();
    }

    public int getState() {
        return mInfiniteCycleManager == null ?
                ViewPager.SCROLL_STATE_IDLE : mInfiniteCycleManager.getState();
    }

    public void notifyDataSetChanged() {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.notifyDataSetChanged();
    }

    public void invalidateTransformer() {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.invalidateTransformer();
    }

    public void postInvalidateTransformer() {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.postInvalidateTransformer();
    }

    public void startAutoScroll(final boolean isAutoScrollPositive) {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.startAutoScroll(isAutoScrollPositive);
    }

    public void stopAutoScroll() {
        if (mInfiniteCycleManager != null) mInfiniteCycleManager.stopAutoScroll();
    }
}
