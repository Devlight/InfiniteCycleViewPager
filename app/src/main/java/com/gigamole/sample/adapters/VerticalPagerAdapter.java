package com.gigamole.sample.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gigamole.sample.R;
import com.gigamole.sample.utils.Utils;

import static com.gigamole.sample.utils.Utils.setupImage;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class VerticalPagerAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    "https://github.com/DevLight-Mobile-Agency/PulseView",
                    "https://lh4.googleusercontent.com/-mqVBaXK0XM0/VyhroJmmU7I/AAAAAAAACUs/OmfzeRZsYNQhNAdgFmKvFIU5XDiJ3-z3wCL0B/w401-h689-no/pv.gif"
            ),
            new Utils.LibraryObject(
                    "https://github.com/DevLight-Mobile-Agency/ArcProgressStackView",
                    "https://lh3.googleusercontent.com/-5wahrBgdCTw/VuwHChXS3FI/AAAAAAAACNQ/eqpqhfnN5G0l3EnETDQ_I5qu4kqQYSysA/w327-h551-no/apsv_preview_size_cb.gif"
            ),
            new Utils.LibraryObject(
                    "https://github.com/DevLight-Mobile-Agency/CutIntoLayout",
                    "https://lh3.googleusercontent.com/fWz9orE3M8vaAuzkxHi0F2m23iuHUYlXmG-lgonEFCU=w210-h282-no"
            ),
            new Utils.LibraryObject(
                    "https://github.com/DevLight-Mobile-Agency/TintLayout",
                    "https://lh3.googleusercontent.com/zwCLyDjZMVM2O571j6gwReHbkAolr7DV3XyKy5hqTug=s308-no"
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private Utils.LibraryObject mLibraryObject;

    public VerticalPagerAdapter(final Context context, final Utils.LibraryObject libraryObject) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mLibraryObject = libraryObject;
    }

    @Override
    public int getCount() {
        return mLibraryObject == null ? LIBRARIES.length : mLibraryObject.getImages().length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view = mLayoutInflater.inflate(R.layout.item, container, false);

        if (mLibraryObject == null)
            setupImage(mContext, (ImageView) view.findViewById(R.id.image), LIBRARIES[position]);
        else
            setupImage(mContext, (ImageView) view.findViewById(R.id.image), mLibraryObject, position);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
