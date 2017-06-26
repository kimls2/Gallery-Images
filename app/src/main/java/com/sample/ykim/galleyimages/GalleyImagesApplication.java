package com.sample.ykim.galleyimages;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.sample.ykim.galleyimages.data.DataManager;
import com.sample.ykim.galleyimages.injection.component.ApplicationComponent;
import com.sample.ykim.galleyimages.injection.component.DaggerApplicationComponent;
import com.sample.ykim.galleyimages.injection.module.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by ykim on 2017. 4. 11..
 */

public class GalleyImagesApplication extends Application {

  @Inject DataManager mDataManager;
  ApplicationComponent mApplicationComponent;

  private RefWatcher refWatcher;

  public static RefWatcher getRefWatcher(Context context) {
    GalleyImagesApplication application = (GalleyImagesApplication) context.getApplicationContext();
    return application.refWatcher;
  }

  @Override public void onCreate() {
    super.onCreate();
    mApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    mApplicationComponent.inject(this);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
      if (LeakCanary.isInAnalyzerProcess(this)) {
        return;
      }
      refWatcher = LeakCanary.install(this);
    } else {
      Fabric.with(this, new Crashlytics());
    }
  }

  public static GalleyImagesApplication get(Context context) {
    return (GalleyImagesApplication) context.getApplicationContext();
  }

  public ApplicationComponent getComponent() {
    return mApplicationComponent;
  }
}
