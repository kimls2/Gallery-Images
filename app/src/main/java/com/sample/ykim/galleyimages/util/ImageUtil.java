package com.sample.ykim.galleyimages.util;

import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sample.ykim.galleyimages.R;

/**
 * Created by ykim on 2017. 4. 14..
 */

public class ImageUtil {
  public static void loadImage(ImageView imageView, String url) {
    Glide.with(imageView.getContext()) //
        .load(url)  //
        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)  //
        .placeholder(R.drawable.placeholer_rectangle)  //
        .error(R.drawable.error_image)  //
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)  //
        .into(imageView);
  }

  public static void loadImageWithProgress(ImageView imageView, String url, View progressBar) {
    progressBar.setVisibility(View.VISIBLE);
    Glide.with(imageView.getContext()) //
        .load(url)  //
        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)  //
        .placeholder(R.drawable.placeholer_rectangle)  //
        .error(R.drawable.error_image)  //
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)  //
        .listener(new RequestListener<String, GlideDrawable>() {
          @Override
          public boolean onException(Exception e, String model, Target<GlideDrawable> target,
              boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
          }

          @Override public boolean onResourceReady(GlideDrawable resource, String model,
              Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
          }
        }).into(imageView);
  }
}
