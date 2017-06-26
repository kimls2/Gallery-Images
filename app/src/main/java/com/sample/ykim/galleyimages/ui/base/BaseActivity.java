package com.sample.ykim.galleyimages.ui.base;

import android.support.v7.app.AppCompatActivity;
import com.sample.ykim.galleyimages.BuildConfig;
import com.sample.ykim.galleyimages.GalleyImagesApplication;
import com.sample.ykim.galleyimages.injection.component.ActivityComponent;
import com.sample.ykim.galleyimages.injection.component.DaggerActivityComponent;

/**
 * Created by ykim on 2017. 4. 11..
 */

public class BaseActivity extends AppCompatActivity {

  private ActivityComponent mActivityComponent;

  public ActivityComponent activityComponent() {
    if (mActivityComponent == null) {
      mActivityComponent = DaggerActivityComponent.builder()
          .applicationComponent(GalleyImagesApplication.get(this).getComponent())
          .build();
    }
    return mActivityComponent;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (BuildConfig.DEBUG) {
      GalleyImagesApplication.getRefWatcher(this).watch(this);
    }
  }
}
