package com.sample.ykim.galleyimages.ui.misc;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by ykim on 2017. 4. 11..
 */

public final class ThumbnailImageView extends AppCompatImageView {
  private float mAspectRatio;

  public ThumbnailImageView(Context context) {
    super(context);
  }

  public ThumbnailImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ThumbnailImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setAspectRatio(float aspectRatio) {
    mAspectRatio = aspectRatio;
    requestLayout();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = (int) ((float) width / mAspectRatio);
    setMeasuredDimension(width, height);
  }
}