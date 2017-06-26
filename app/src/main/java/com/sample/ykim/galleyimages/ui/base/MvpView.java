package com.sample.ykim.galleyimages.ui.base;

import android.support.annotation.UiThread;

/**
 * Created by ykim on 2017. 4. 11..
 */

public interface MvpView {

  @UiThread void showLoading(boolean show);

  void showErrorMessage(String message);
}
