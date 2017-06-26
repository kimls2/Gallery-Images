package com.sample.ykim.galleyimages.ui.main;

import android.view.View;
import android.view.ViewGroup;
import com.sample.ykim.galleyimages.data.model.GalleryImage;
import com.sample.ykim.galleyimages.ui.base.RecyclerViewAdapterBase;
import com.sample.ykim.galleyimages.ui.base.ViewWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykim on 2017. 4. 11..
 */

class MainAdapter extends RecyclerViewAdapterBase<GalleryImage, View> {

  private final AdapterItemCallback mItemCallback;
  private boolean mGridLayout = true;

  MainAdapter(AdapterItemCallback itemCallback) {
    mItemCallback = itemCallback;
    items = new ArrayList<>();
  }

  void setData(List<GalleryImage> galleryImages) {
    items = galleryImages;
    notifyDataSetChanged();
  }

  void addData(List<GalleryImage> galleryImages) {
    int beforeAddingSize = items.size();
    items.addAll(galleryImages);
    notifyItemRangeInserted(beforeAddingSize, galleryImages.size());
  }

  void setLayout(boolean gridLayout) {
    mGridLayout = gridLayout;
    notifyDataSetChanged();
  }

  @Override protected View onCreateItemView(ViewGroup parent, int viewType) {
    return GalleyImageView.build(parent.getContext(), mItemCallback);
  }

  @Override public void onBindViewHolder(ViewWrapper<View> holder, int position) {
    GalleyImageView itemView = (GalleyImageView) holder.getView();
    itemView.bindTo(items.get(position), mGridLayout);
  }
}
