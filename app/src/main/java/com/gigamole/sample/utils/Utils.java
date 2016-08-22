package com.gigamole.sample.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.stanko.tools.IntentHelper;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class Utils {

    private final static View.OnClickListener mOnImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            IntentHelper.openUrlInBrowser(view.getContext(), (String) view.getTag(view.getId()));
        }
    };

    public static void setupImage(final Context context, final ImageView imageView, final LibraryObject libraryObject) {
        setupImage(context, imageView, libraryObject, -1);
    }

    public static void setupImage(final Context context, final ImageView imageView, final LibraryObject libraryObject, final int position) {

        imageView.setTag(imageView.getId(), libraryObject.getUrl());
        imageView.setOnClickListener(mOnImageClickListener);

        Glide.with(context)
                .load(position == -1 ? libraryObject.getImage() : libraryObject.getImages()[position])
                .asBitmap()
                .centerCrop()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(final Exception e, final String model, final Target<Bitmap> target, final boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(final Bitmap resource, final String model, final Target<Bitmap> target, final boolean isFromMemoryCache, final boolean isFirstResource) {
                        final ImageViewTarget imageViewTarget = (ImageViewTarget) target;
                        return new DrawableCrossFadeFactory<>()
                                .build(isFromMemoryCache, isFirstResource)
                                .animate(
                                        new BitmapDrawable(
                                                imageViewTarget.getView().getResources(),
                                                resource
                                        ),
                                        imageViewTarget
                                );
                    }
                })
                .into(imageView);
    }

    public static class LibraryObject {

        private String mUrl;
        private String mImage;
        private String[] mImages = null;

        public LibraryObject(final String url, final String image) {
            mUrl = url;
            mImage = image;
        }

        public LibraryObject(final String url, final String[] images) {
            mUrl = url;
            mImages = images;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getImage() {
            return mImage;
        }

        public String[] getImages() {
            return mImages;
        }
    }
}
