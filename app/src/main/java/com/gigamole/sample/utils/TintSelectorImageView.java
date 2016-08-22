package com.gigamole.sample.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by GIGAMOLE on 8/22/16.
 */
public class TintSelectorImageView extends ImageView {

    private final static int TINT_SELECTOR_COLOR = Color.parseColor("#506DD3CE");

    public TintSelectorImageView(final Context context) {
        super(context);
        setClickable(true);
    }

    public TintSelectorImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    public TintSelectorImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setColorFilter(TINT_SELECTOR_COLOR, PorterDuff.Mode.SRC_OVER);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            default:
                clearColorFilter();
                postInvalidate();
                break;
        }

        return super.onTouchEvent(event);
    }

}
