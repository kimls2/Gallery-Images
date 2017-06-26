package com.sample.ykim.galleyimages.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by ykim on 2017. 4. 11..
 */
public abstract class RecyclerViewAdapterBase<T, V extends View>
    extends RecyclerView.Adapter<ViewWrapper<V>> {
  protected List<T> items;

  @Override public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewWrapper<>(onCreateItemView(parent, viewType));
  }

  @Override public int getItemCount() {
    return items.size();
  }

  protected abstract V onCreateItemView(ViewGroup parent, int viewType);
}
