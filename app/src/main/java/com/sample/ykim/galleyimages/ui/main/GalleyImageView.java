package com.sample.ykim.galleyimages.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sample.ykim.galleyimages.R;
import com.sample.ykim.galleyimages.data.model.GalleryImage;
import com.sample.ykim.galleyimages.ui.misc.ThumbnailImageView;
import com.sample.ykim.galleyimages.util.ImageUtil;

/**
 * Created by ykim on 2017. 4. 11..
 */

@SuppressLint("ViewConstructor") public class GalleyImageView extends LinearLayout {

  private final AdapterItemCallback mAdapterItemCallback;

  @BindView(R.id.thumbnail_iv) ThumbnailImageView thumbnailIv;
  @BindView(R.id.description_tv) TextView descriptionTv;
  @BindView(R.id.gallery_image_item_root) LinearLayout root;
  @BindView(R.id.card_view) CardView cardView;
  @BindView(R.id.gif_iv) ImageView gifIv;

  public GalleyImageView(@NonNull Context context, AdapterItemCallback adapterItemCallback) {
    super(context);
    mAdapterItemCallback = adapterItemCallback;
  }

  public static GalleyImageView build(Context context, AdapterItemCallback adapterItemCallback) {
    GalleyImageView instance = new GalleyImageView(context, adapterItemCallback);
    instance.onFinishInflate();
    return instance;
  }

  @Override protected void onFinishInflate() {
    inflate(getContext(), R.layout.item_gallery_image, this);
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void bindTo(GalleryImage galleryImage, boolean gridLayout) {
    if (gridLayout) {
      thumbnailIv.setAspectRatio(1.0f);
    } else {
      thumbnailIv.setAspectRatio(galleryImage.getAspectRation());
    }
    String imageUrl = galleryImage.getThumbnailSize(GalleryImage.MEDIUM_THUMBNAIL);
    ImageUtil.loadImage(thumbnailIv, imageUrl);
    if (galleryImage.isAnimated()) {
      gifIv.setVisibility(VISIBLE);
    } else {
      gifIv.setVisibility(GONE);
    }

    if (galleryImage.getDescription() == null) {
      descriptionTv.setVisibility(GONE);
    } else {
      descriptionTv.setText(galleryImage.getDescription());
      descriptionTv.setVisibility(VISIBLE);
    }

    cardView.setOnClickListener(v -> mAdapterItemCallback.itemOnClicked(galleryImage));
  }
}
