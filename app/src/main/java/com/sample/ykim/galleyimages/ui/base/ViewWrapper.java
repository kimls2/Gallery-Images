package com.sample.ykim.galleyimages.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ykim on 2017. 4. 11..
 */
public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {
  public V view;

  public ViewWrapper(V itemView) {
    super(itemView);
    view = itemView;
  }

  public V getView() {
    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    return view;
  }
}
