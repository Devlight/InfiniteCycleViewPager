package com.gigamole.infinitecycleviewpager;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

import static android.support.v4.view.ViewPager.GONE;
import static android.support.v4.view.ViewPager.OnPageChangeListener;
import static android.support.v4.view.ViewPager.PageTransformer;
import static android.support.v4.view.ViewPager.VISIBLE;
import static android.view.View.OVER_SCROLL_NEVER;
import static com.gigamole.infinitecycleviewpager.InfiniteCyclePagerAdapter.OnNotifyDataSetChangedListener;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
@SuppressWarnings("unused")
class InfiniteCycleManager implements OnNotifyDataSetChangedListener {

    // InfiniteCycleManager constants
    private final static int MIN_CYCLE_COUNT = 3;
    private final static int MIN_POINTER_COUNT = 1;

    // Default ViewPager constants and flags
    protected final static int DEFAULT_OFFSCREEN_PAGE_LIMIT = 2;
    protected final static int DEFAULT_PAGE_MARGIN = 0;
    protected final static boolean DEFAULT_DISABLE_FLAG = false;
    protected final static boolean DEFAULT_ENABLE_FLAG = true;

    // Default attributes constants
    private final static float DEFAULT_MIN_SCALE = 0.55F;
    private final static float DEFAULT_MAX_SCALE = 0.8F;
    private final static int DEFAULT_MIN_PAGE_SCALE_OFFSET = 30;
    private final static int DEFAULT_CENTER_PAGE_SCALE_OFFSET = 50;
    private final static boolean DEFAULT_IS_MEDIUM_SCALED = true;
    private final static int DEFAULT_SCROLL_DURATION = 500;

    private Context mContext;

    // Infinite ViewPager and adapter
    private ViewPageable mViewPageable;
    private View mCastViewPageable;
    private InfiniteCyclePagerAdapter mInfiniteCyclePagerAdapter;

    // Inner and outer state of scrolling
    private PageScrolledState mInnerPageScrolledState = PageScrolledState.IDLE;
    private PageScrolledState mOuterPageScrolledState = PageScrolledState.IDLE;

    // Page scrolled info positions
    private float mPageScrolledPositionOffset;
    private float mPageScrolledPosition;

    // When item count equals to 3 we need stack count for know is item on position -2 or 2 is placed
    private int mStackCount;
    // Item count of original adapter
    private int mItemCount;

    // Flag to know is left page need bring to front for correct scrolling present
    private boolean mIsLeftPageBringToFront;
    // Flag to know is right page need bring to front for correct scrolling present
    private boolean mIsRightPageBringToFront;
    // Detect if was minus one position of transform page for correct handle of page bring to front
    private boolean mWasMinusOne;
    // Detect if was plus one position of transform page for correct handle of page bring to front
    private boolean mWasPlusOne;

    // Hit rect of view bounds to detect inside touch
    private final Rect mHitRect = new Rect();

    // Flag for invalidate transformer side scroll when use setCurrentItem() method
    private boolean mIsInitialItem;
    // Flag for setCurrentItem to zero of half the virtual count when set adapter
    private boolean mIsAdapterInitialPosition;
    // Flag for data set changed callback to invalidateTransformer()
    private boolean mIsDataSetChanged;
    // Detect is ViewPager state
    private int mState;

    // Custom transform listener
    private OnInfiniteCyclePageTransformListener mOnInfiniteCyclePageTransformListener;

    // Page scale offset at minimum scale(left and right bottom pages)
    private float mMinPageScaleOffset;
    // Page scale offset at maximum scale(center top page)
    private float mCenterPageScaleOffset;

    // Minimum page scale(left and right pages)
    private float mMinPageScale;
    // Maximum page scale(center page)
    private float mMaxPageScale;
    // Center scale by when scroll position is on half
    private float mCenterScaleBy;

    // Use medium scale or just from max to min
    private boolean mIsMediumScaled = false;
    // Detect is vertical orientation
    private boolean mIsVertical;

    // Scroll duration of snapping
    private int mScrollDuration;
    // Duration for which a page will be shown before moving on to next one when {@link mIsAutoScroll} is TRUE
    private int mPageDuration;
    // Interpolator of snapping
    private Interpolator mInterpolator;

    // Auto scroll values
    private boolean mIsAutoScroll;
    private boolean mIsAutoScrollPositive;
    // Auto scroll handlers
    private final Handler mAutoScrollHandler = new Handler();
    private final Runnable mAutoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mIsAutoScroll) return;
            mViewPageable.setCurrentItem(getRealItem() + (mIsAutoScrollPositive ? 1 : -1));
            mAutoScrollHandler.postDelayed(this, mPageDuration);
        }
    };

    public InfiniteCycleManager(
            final Context context,
            final ViewPageable viewPageable,
            final AttributeSet attributeSet
    ) {
        mContext = context;
        mIsVertical = viewPageable instanceof VerticalViewPager;

        mViewPageable = viewPageable;
        mCastViewPageable = (View) viewPageable;

        // Set default InfiniteViewPager
        mViewPageable.setPageTransformer(false, getInfinityCyclePageTransformer());
        mViewPageable.addOnPageChangeListener(mInfinityCyclePageChangeListener);
        mViewPageable.setClipChildren(DEFAULT_DISABLE_FLAG);
        mViewPageable.setDrawingCacheEnabled(DEFAULT_DISABLE_FLAG);
        mViewPageable.setWillNotCacheDrawing(DEFAULT_ENABLE_FLAG);
        mViewPageable.setPageMargin(DEFAULT_PAGE_MARGIN);
        mViewPageable.setOffscreenPageLimit(DEFAULT_OFFSCREEN_PAGE_LIMIT);
        mViewPageable.setOverScrollMode(OVER_SCROLL_NEVER);

        // Reset scroller and process attribute set
        resetScroller();
        processAttributeSet(attributeSet);
    }

    public void processAttributeSet(final AttributeSet attributeSet) {
        if (attributeSet == null) return;
        final TypedArray typedArray = mContext.obtainStyledAttributes(
                attributeSet, mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager :
                        R.styleable.HorizontalInfiniteCycleViewPager
        );
        try {
            setMinPageScaleOffset(
                    typedArray.getDimension(
                            mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_min_page_scale_offset :
                                    R.styleable.HorizontalInfiniteCycleViewPager_icvp_min_page_scale_offset,
                            DEFAULT_MIN_PAGE_SCALE_OFFSET
                    )
            );
            setCenterPageScaleOffset(
                    typedArray.getDimension(
                            mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_center_page_scale_offset :
                                    R.styleable.HorizontalInfiniteCycleViewPager_icvp_center_page_scale_offset,
                            DEFAULT_CENTER_PAGE_SCALE_OFFSET
                    )
            );
            setMinPageScale(
                    typedArray.getFloat(
                            mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_min_page_scale :
                                    R.styleable.HorizontalInfiniteCycleViewPager_icvp_min_page_scale,
                            DEFAULT_MIN_SCALE
                    )
            );
            setMaxPageScale(
                    typedArray.getFloat(
                            mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_max_page_scale :
                                    R.styleable.HorizontalInfiniteCycleViewPager_icvp_max_page_scale,
                            DEFAULT_MAX_SCALE
                    )
            );
            setMediumScaled(
                    typedArray.getBoolean(
                            mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_medium_scaled :
                                    R.styleable.HorizontalInfiniteCycleViewPager_icvp_medium_scaled,
                            DEFAULT_IS_MEDIUM_SCALED
                    )
            );
            setScrollDuration(
                    typedArray.getInteger(
                            mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_scroll_duration :
                                    R.styleable.HorizontalInfiniteCycleViewPager_icvp_scroll_duration,
                            DEFAULT_SCROLL_DURATION
                    )
            );
            setPageDuration(
                    typedArray.getInteger(
                            mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_page_duration :
                                    R.styleable.HorizontalInfiniteCycleViewPager_icvp_page_duration,
                            DEFAULT_SCROLL_DURATION
                    )
            );

            // Retrieve interpolator
            Interpolator interpolator = null;
            try {
                final int interpolatorId = typedArray.getResourceId(
                        mIsVertical ? R.styleable.VerticalInfiniteCycleViewPager_icvp_interpolator :
                                R.styleable.HorizontalInfiniteCycleViewPager_icvp_interpolator, 0
                );
                interpolator = interpolatorId == 0 ? null :
                        AnimationUtils.loadInterpolator(mContext, interpolatorId);
            } catch (Resources.NotFoundException exception) {
                interpolator = null;
                exception.printStackTrace();
            } finally {
                setInterpolator(interpolator);
            }
        } finally {
            typedArray.recycle();
        }
    }

    public float getMinPageScaleOffset() {
        return mMinPageScaleOffset;
    }

    public void setMinPageScaleOffset(final float minPageScaleOffset) {
        mMinPageScaleOffset = minPageScaleOffset;
    }

    public float getCenterPageScaleOffset() {
        return mCenterPageScaleOffset;
    }

    public void setCenterPageScaleOffset(final float centerPageScaleOffset) {
        mCenterPageScaleOffset = centerPageScaleOffset;
    }

    public float getMinPageScale() {
        return mMinPageScale;
    }

    public void setMinPageScale(final float minPageScale) {
        mMinPageScale = minPageScale;
        resetScaleBy();
    }

    public float getMaxPageScale() {
        return mMaxPageScale;
    }

    public void setMaxPageScale(final float maxPageScale) {
        mMaxPageScale = maxPageScale;
        resetScaleBy();
    }

    public boolean isMediumScaled() {
        return mIsMediumScaled;
    }

    public void setMediumScaled(final boolean mediumScaled) {
        mIsMediumScaled = mediumScaled;
    }

    public int getScrollDuration() {
        return mScrollDuration;
    }

    public void setScrollDuration(final int scrollDuration) {
        mScrollDuration = scrollDuration;
        resetScroller();
    }

    public int getPageDuration() {
        return mPageDuration;
    }

    public void setPageDuration(final int pageDuration) {
        mPageDuration = pageDuration;
        resetScroller();
    }

    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(final Interpolator interpolator) {
        mInterpolator = interpolator == null ? new SpringInterpolator() : interpolator;
        resetScroller();
    }

    public boolean isVertical() {
        return mIsVertical;
    }

    public int getState() {
        return mState;
    }

    public OnInfiniteCyclePageTransformListener getOnInfiniteCyclePageTransformListener() {
        return mOnInfiniteCyclePageTransformListener;
    }

    public void setOnInfiniteCyclePageTransformListener(
            final OnInfiniteCyclePageTransformListener onInfiniteCyclePageTransformListener
    ) {
        mOnInfiniteCyclePageTransformListener = onInfiniteCyclePageTransformListener;
    }

    public InfiniteCyclePageTransformer getInfinityCyclePageTransformer() {
        return new InfiniteCyclePageTransformer();
    }

    public InfiniteCyclePagerAdapter getInfiniteCyclePagerAdapter() {
        return mInfiniteCyclePagerAdapter;
    }

    public PagerAdapter setAdapter(final PagerAdapter adapter) {
        // If adapter count bigger then 2 need to set InfiniteCyclePagerAdapter
        if (adapter != null && adapter.getCount() >= MIN_CYCLE_COUNT) {
            mItemCount = adapter.getCount();
            mInfiniteCyclePagerAdapter = new InfiniteCyclePagerAdapter(adapter);
            mInfiniteCyclePagerAdapter.setOnNotifyDataSetChangedListener(this);
            return mInfiniteCyclePagerAdapter;
        } else {
            if (mInfiniteCyclePagerAdapter != null) {
                mInfiniteCyclePagerAdapter.setOnNotifyDataSetChangedListener(null);
                mInfiniteCyclePagerAdapter = null;
            }
            return adapter;
        }
    }

    // We are disable multitouch on ViewPager and settling scroll, also we disable outside drag
    public boolean onTouchEvent(final MotionEvent event) {
        if (mViewPageable.getAdapter() == null || mViewPageable.getAdapter().getCount() == 0)
            return false;
        if (mIsAutoScroll || mIsInitialItem || mViewPageable.isFakeDragging()) return false;
        if (event.getPointerCount() > MIN_POINTER_COUNT || !mViewPageable.hasWindowFocus())
            event.setAction(MotionEvent.ACTION_UP);
        checkHitRect(event);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return onTouchEvent(event);
    }

    // When not has window focus clamp to nearest position
    public void onWindowFocusChanged(final boolean hasWindowFocus) {
        if (hasWindowFocus) invalidateTransformer();
    }

    // Set current item where you put original adapter position and this method calculate nearest
    // position to scroll from center if at first initial position or nearest position of old position
    public int setCurrentItem(final int item) {
        mIsInitialItem = true;

        if (mViewPageable.getAdapter() == null ||
                mViewPageable.getAdapter().getCount() < MIN_CYCLE_COUNT) return item;

        final int count = mViewPageable.getAdapter().getCount();
        if (mIsAdapterInitialPosition) {
            mIsAdapterInitialPosition = false;
            return ((mInfiniteCyclePagerAdapter.getCount() / 2) / count) * count;
        } else return mViewPageable.getCurrentItem() + Math.min(count, item) - getRealItem();
    }

    // Need to get current position of original adapter. We cant override getCurrentItem() method,
    // because ViewPager must have original item count relative to virtual adapter count
    public int getRealItem() {
        if (mViewPageable.getAdapter() == null ||
                mViewPageable.getAdapter().getCount() < MIN_CYCLE_COUNT)
            return mViewPageable.getCurrentItem();
        return mInfiniteCyclePagerAdapter.getVirtualPosition(mViewPageable.getCurrentItem());
    }

    // Now you can call notify data on ViewPager nor adapter to invalidate all of positions
    public void notifyDataSetChanged() {
        if (mInfiniteCyclePagerAdapter == null) {
            mViewPageable.getAdapter().notifyDataSetChanged();
            mIsDataSetChanged = true;
        } else mInfiniteCyclePagerAdapter.notifyDataSetChanged();
        postInvalidateTransformer();
    }

    // If you need to update transformer call this method, which is trigger fake scroll
    public void invalidateTransformer() {
        if (mViewPageable.getAdapter() == null || mViewPageable.getAdapter().getCount() == 0 ||
                mViewPageable.getChildCount() == 0) return;
        if (mViewPageable.beginFakeDrag()) {
            mViewPageable.fakeDragBy(0.0F);
            mViewPageable.endFakeDrag();
        }
    }

    public void postInvalidateTransformer() {
        mViewPageable.post(new Runnable() {
            @Override
            public void run() {
                invalidateTransformer();
                mIsDataSetChanged = false;
            }
        });
    }

    // Enable hardware layer when transform pages
    private void enableHardwareLayer(final View v) {
        final int layerType = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ?
                View.LAYER_TYPE_NONE : View.LAYER_TYPE_HARDWARE;
        if (v.getLayerType() != layerType) v.setLayerType(layerType, null);
    }

    // Disable hardware layer when idle
    private void disableHardwareLayers() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) return;
        for (int i = 0; i < mViewPageable.getChildCount(); i++) {
            final View child = mViewPageable.getChildAt(i);
            if (child.getLayerType() != View.LAYER_TYPE_NONE)
                child.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    // Detect is we are idle in pageScrolled() callback, not in scrollStateChanged()
    private boolean isSmallPositionOffset(float positionOffset) {
        return Math.abs(positionOffset) < 0.0001F;
    }

    // Check view bounds touch
    private void checkHitRect(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mHitRect.set(
                    mCastViewPageable.getLeft(), mCastViewPageable.getTop(),
                    mCastViewPageable.getRight(), mCastViewPageable.getBottom()
            );
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && !mHitRect.contains(
                mCastViewPageable.getLeft() + (int) event.getX(),
                mCastViewPageable.getTop() + (int) event.getY()
        )) event.setAction(MotionEvent.ACTION_UP);
    }

    // Reset scroller to own
    private void resetScroller() {
        if (mViewPageable == null) return;
        try {
            final Field scroller = mIsVertical ? VerticalViewPager.class.getDeclaredField("mScroller") :
                    ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            final InfiniteCycleScroller infiniteCycleScroller =
                    new InfiniteCycleScroller(mContext, mInterpolator);
            infiniteCycleScroller.setDuration(mScrollDuration);
            scroller.set(mViewPageable, infiniteCycleScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reset pager when reset adapter
    public void resetPager() {
        mIsAdapterInitialPosition = true;
        mViewPageable.setCurrentItem(0);
        postInvalidateTransformer();
    }

    // Recalculate scale by variable
    private void resetScaleBy() {
        mCenterScaleBy = (mMaxPageScale - mMinPageScale) * 0.5F;
    }

    // Start auto scroll
    public void startAutoScroll(final boolean isAutoScrollPositive) {
        if (mIsAutoScroll && isAutoScrollPositive == mIsAutoScrollPositive) return;
        mIsAutoScroll = true;
        mIsAutoScrollPositive = isAutoScrollPositive;

        mAutoScrollHandler.removeCallbacks(mAutoScrollRunnable);
        mAutoScrollHandler.post(mAutoScrollRunnable);
    }

    // Stop auto scroll
    public void stopAutoScroll() {
        if (!mIsAutoScroll) return;
        mIsAutoScroll = false;
        mAutoScrollHandler.removeCallbacks(mAutoScrollRunnable);
    }

    @Override
    public void onChanged() {
        mIsDataSetChanged = true;
    }

    // The main presenter feature of this library is this InfiniteCyclePageTransformer.
    // The logic is based to cycle items like carousel mode. There we don't have direct method
    // to set z-index, so we need to handle only one method bringToFront().
    protected class InfiniteCyclePageTransformer implements PageTransformer {

        @Override
        public void transformPage(final View page, final float position) {
            if (mOnInfiniteCyclePageTransformListener != null)
                mOnInfiniteCyclePageTransformListener.onPreTransform(page, position);

            // Handle page layer and bounds visibility
            enableHardwareLayer(page);
            if (mItemCount == MIN_CYCLE_COUNT) {
                if (position > 2.0F || position < -2.0F ||
                        (mStackCount != 0 && position > 1.0F) ||
                        (mStackCount != 0 && position < -1.0F)) {
                    page.setVisibility(GONE);
                    return;
                } else page.setVisibility(VISIBLE);
            }

            final float pageSize = mIsVertical ? page.getMeasuredHeight() : page.getMeasuredWidth();

            // Page offsets relative to scale
            final float pageMinScaleOffset = pageSize * mMinPageScale;
            final float pageSubScaleByOffset = pageSize * mCenterScaleBy;

            // Page offsets from bounds
            final float pageMinScaleEdgeOffset = (pageSize - pageMinScaleOffset) * 0.5F;
            final float pageMaxScaleEdgeOffset = (pageSize - (pageSize * mMaxPageScale)) * 0.5F;
            final float pageSubScaleEdgeOffset =
                    (pageSize - (pageSize * (mMinPageScale + mCenterScaleBy))) * 0.5F;

            final float scale;
            final float translation;

            // Detect when the count <= 3 and another page of side stack not placed
            if (mItemCount < MIN_CYCLE_COUNT + 1 && mStackCount == 0 &&
                    position > -2.0F && position < -1.0F) {
                final float fraction = -1.0F - position;

                scale = mMinPageScale;
                translation = (pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset) +
                        (pageSize * 2.0F - pageMinScaleOffset - mMinPageScaleOffset * 2.0F) * fraction;

                mStackCount++;
            }
            // Detect when the count > 3 and pages at the center of bottom
            else if (mItemCount > MIN_CYCLE_COUNT && position >= -2.0F && position < -1.0F) {
                final float fraction = 1.0F + (position + 1.0F);

                scale = mMinPageScale;
                translation = (pageSize * 2.0F) - ((pageSize +
                        pageMinScaleEdgeOffset - mMinPageScaleOffset) * fraction);

            }
            // Transform from minimum scale to medium scale or max
            else if (position >= -1.0F && position <= -0.5F) {
                final float positiveFraction = 1.0F + (position + 0.5F) * 2.0F;
                final float negativeFraction = 1.0F - positiveFraction;

                if (mIsMediumScaled) {
                    final float startOffset = pageSize - pageSubScaleByOffset -
                            pageMaxScaleEdgeOffset + mMinPageScaleOffset;

                    scale = (mMinPageScale + mCenterScaleBy) - (mCenterScaleBy * negativeFraction);
                    translation = startOffset - ((startOffset - pageSubScaleEdgeOffset +
                            mCenterPageScaleOffset) * positiveFraction);
                } else {
                    final float startOffset =
                            pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset;

                    scale = (mMaxPageScale) - ((mMaxPageScale - mMinPageScale) * negativeFraction);
                    translation = (startOffset) - ((startOffset - pageMaxScaleEdgeOffset +
                            mCenterPageScaleOffset) * positiveFraction);
                }
            }
            // Transform from center or max to max
            else if (position >= -0.5F && position <= 0.0F) {
                final float fraction = -position * 2.0F;

                scale = mMaxPageScale - (mIsMediumScaled ? mCenterScaleBy * fraction : 0.0F);
                translation = ((mIsMediumScaled ? pageSubScaleEdgeOffset : pageMaxScaleEdgeOffset) -
                        mCenterPageScaleOffset) * fraction;
            }
            // Transform from max to center or max
            else if (position >= 0.0F && position <= 0.5F) {
                final float negativeFraction = position * 2.0F;
                final float positiveFraction = 1.0F - negativeFraction;

                scale = !mIsMediumScaled ? mMaxPageScale :
                        (mMinPageScale + mCenterScaleBy) + (mCenterScaleBy * positiveFraction);
                translation = (-(mIsMediumScaled ? pageSubScaleEdgeOffset : pageMaxScaleEdgeOffset) +
                        mCenterPageScaleOffset) * negativeFraction;
            }
            // Transform from center or max to min
            else if (position >= 0.5F && position <= 1.0F) {
                final float negativeFraction = (position - 0.5F) * 2.0F;
                final float positiveFraction = 1.0F - negativeFraction;

                if (mIsMediumScaled) {
                    scale = mMinPageScale + (mCenterScaleBy * positiveFraction);
                    translation = (-pageSubScaleEdgeOffset + mCenterPageScaleOffset) + ((-pageSize +
                            pageSubScaleByOffset + pageMaxScaleEdgeOffset + pageSubScaleEdgeOffset
                            - mMinPageScaleOffset - mCenterPageScaleOffset) * negativeFraction);
                } else {
                    scale = mMinPageScale + ((mMaxPageScale - mMinPageScale) * positiveFraction);
                    translation = (-pageMaxScaleEdgeOffset + mCenterPageScaleOffset) +
                            ((-pageSize + pageMaxScaleEdgeOffset + pageMinScaleEdgeOffset -
                                    mMinPageScaleOffset - mCenterPageScaleOffset) * negativeFraction);
                }
            }
            // Detect when the count > 3 and pages at the center of bottom
            else if (mItemCount > MIN_CYCLE_COUNT && position > 1.0F && position <= 2.0F) {
                final float negativeFraction = 1.0F + (position - 1.0F);
                final float positiveFraction = 1.0F - negativeFraction;

                scale = mMinPageScale;
                translation = -(pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset) +
                        ((pageSize + pageMinScaleEdgeOffset - mMinPageScaleOffset) * positiveFraction);
            }
            // Detect when the count <= 3 and another page of side stack not placed
            else if (mItemCount < MIN_CYCLE_COUNT + 1 && mStackCount == 0 &&
                    position > 1.0F && position < 2.0F) {
                final float fraction = 1.0F - position;

                scale = mMinPageScale;
                translation = -(pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset) +
                        ((pageSize * 2.0F - pageMinScaleOffset - mMinPageScaleOffset * 2.0F) * fraction);

                mStackCount++;
            } else {
                // Reset values
                scale = mMinPageScale;
                translation = 0.0F;
            }

            // Scale page
            ViewCompat.setScaleX(page, scale);
            ViewCompat.setScaleY(page, scale);

            // Translate page
            if (mIsVertical) ViewCompat.setTranslationY(page, translation);
            else ViewCompat.setTranslationX(page, translation);

            boolean needBringToFront = false;
            if (mItemCount == MIN_CYCLE_COUNT - 1) mIsLeftPageBringToFront = true;

            // Switch to handle what direction we move to know how need bring to front out pages
            switch (mOuterPageScrolledState) {
                case GOING_LEFT:
                    // Reset left page is bring
                    mIsLeftPageBringToFront = false;
                    // Now we handle where we scroll in outer and inner left direction
                    if (mInnerPageScrolledState == PageScrolledState.GOING_LEFT) {
                        // This is another flag which detect if right was not bring to front
                        // and set positive flag
                        if (position > -0.5F && position <= 0.0F) {
                            if (!mIsRightPageBringToFront) {
                                mIsRightPageBringToFront = true;
                                needBringToFront = true;
                            }
                        }
                        // Position of center page and we need bring to front immediately
                        else if (position >= 0.0F && position < 0.5F) needBringToFront = true;
                            // If right was not bring we need to set it up and detect if there no bounds
                        else if (position > 0.5F && position < 1.0F && !mIsRightPageBringToFront &&
                                mViewPageable.getChildCount() > MIN_CYCLE_COUNT)
                            needBringToFront = true;
                    } else {
                        // We move to the right and detect if position if under half of path
                        if (mPageScrolledPositionOffset < 0.5F &&
                                position > -0.5F && position <= 0.0F) needBringToFront = true;
                    }
                    break;
                case GOING_RIGHT:
                    // Reset right page is bring
                    mIsRightPageBringToFront = false;
                    // Now we handle where we scroll in outer and inner right direction
                    if (mInnerPageScrolledState == PageScrolledState.GOING_RIGHT) {
                        // This is another flag which detect if left was not bring to front
                        // and set positive flag
                        if (position >= 0.0F && position < 0.5F) {
                            if (!mIsLeftPageBringToFront) {
                                mIsLeftPageBringToFront = true;
                                needBringToFront = true;
                            }
                        }
                        // Position of center page and we need bring to front immediately
                        else if (position > -0.5F && position <= 0.0F) needBringToFront = true;
                            // If left was not bring we need to set it up and detect if there no bounds
                        else if (position > -1.0F && position < -0.5F && !mIsLeftPageBringToFront &&
                                mViewPageable.getChildCount() > MIN_CYCLE_COUNT)
                            needBringToFront = true;
                    } else {
                        // We move to the left and detect if position if over half of path
                        if (mPageScrolledPositionOffset > 0.5F &&
                                position >= 0.0F && position < 0.5F) needBringToFront = true;
                    }
                    break;
                default:
                    // If is data set changed we need to hard reset page bring flags
                    if (mIsDataSetChanged) {
                        mIsLeftPageBringToFront = false;
                        mIsRightPageBringToFront = false;
                    }
                    // There is one of the general logic which is calculate
                    // what page must be arrived first from idle state
                    else {
                        // Detect different situations of is there a page was bring or
                        // we just need to bring it again to override drawing order

                        if (!mWasPlusOne && position == 1.0F) mWasPlusOne = true;
                        else if (mWasPlusOne && position == -1.0F) mIsLeftPageBringToFront = true;
                        else if ((!mWasPlusOne && position == -1.0F) ||
                                (mWasPlusOne && mIsLeftPageBringToFront && position == -2.0F))
                            mIsLeftPageBringToFront = false;

                        if (!mWasMinusOne && position == -1.0F) mWasMinusOne = true;
                        else if (mWasMinusOne && position == 1.0F) mIsRightPageBringToFront = true;
                        else if ((!mWasMinusOne && position == 1.0F) ||
                                (mWasMinusOne && mIsRightPageBringToFront && position == 2.0F))
                            mIsRightPageBringToFront = false;
                    }

                    // Always bring to front is center position
                    if (position == 0.0F) needBringToFront = true;
                    break;
            }

            // Bring to front if needed
            if (needBringToFront) {
                page.bringToFront();
                mCastViewPageable.invalidate();
            }

            if (mOnInfiniteCyclePageTransformListener != null)
                mOnInfiniteCyclePageTransformListener.onPostTransform(page, position);
        }
    }

    // OnPageChangeListener which is retrieve info about scroll direction and scroll state
    protected final OnPageChangeListener mInfinityCyclePageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(
                final int position, final float positionOffset, final int positionOffsetPixels
        ) {
            // Reset stack count on each scroll offset
            mStackCount = 0;

            // We need to rewrite states when is dragging and when setCurrentItem() from idle
            if (mState != ViewPager.SCROLL_STATE_SETTLING || mIsInitialItem) {
                // Detect first state from idle
                if (mOuterPageScrolledState == PageScrolledState.IDLE && positionOffset > 0) {
                    mPageScrolledPosition = mViewPageable.getCurrentItem();
                    mOuterPageScrolledState = position == mPageScrolledPosition ?
                            PageScrolledState.GOING_LEFT : PageScrolledState.GOING_RIGHT;
                }

                // Rewrite scrolled state when switch to another edge
                final boolean goingRight = position == mPageScrolledPosition;
                if (mOuterPageScrolledState == PageScrolledState.GOING_LEFT && !goingRight)
                    mOuterPageScrolledState = PageScrolledState.GOING_RIGHT;
                else if (mOuterPageScrolledState == PageScrolledState.GOING_RIGHT && goingRight)
                    mOuterPageScrolledState = PageScrolledState.GOING_LEFT;
            }

            // Rewrite inner dynamic scrolled state
            if (mPageScrolledPositionOffset <= positionOffset)
                mInnerPageScrolledState = PageScrolledState.GOING_LEFT;
            else mInnerPageScrolledState = PageScrolledState.GOING_RIGHT;
            mPageScrolledPositionOffset = positionOffset;

            // Detect if is idle in pageScrolled() callback to transform pages last time
            if ((isSmallPositionOffset(positionOffset) ? 0 : positionOffset) == 0) {
                // Reset states and flags on idle
                disableHardwareLayers();

                mInnerPageScrolledState = PageScrolledState.IDLE;
                mOuterPageScrolledState = PageScrolledState.IDLE;

                mWasMinusOne = false;
                mWasPlusOne = false;
                mIsLeftPageBringToFront = false;
                mIsRightPageBringToFront = false;

                mIsInitialItem = false;
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mState = state;
        }
    };

    // Page scrolled state
    private enum PageScrolledState {
        IDLE, GOING_LEFT, GOING_RIGHT
    }

    // Default spring interpolator
    private final class SpringInterpolator implements Interpolator {

        private final static float FACTOR = 0.5F;

        @Override
        public float getInterpolation(final float input) {
            return (float) (Math.pow(2.0F, -10.0F * input) *
                    Math.sin((input - FACTOR / 4.0F) * (2.0F * Math.PI) / FACTOR) + 1.0F);
        }
    }
}
