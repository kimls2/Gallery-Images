package com.sample.ykim.galleyimages.injection.module;

import android.app.Application;
import android.content.Context;
import com.sample.ykim.galleyimages.data.remote.ImgurService;
import com.sample.ykim.galleyimages.injection.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {

  protected final Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides Application provideApplication() {
    return mApplication;
  }

  @Provides @ApplicationContext Context provideContext() {
    return mApplication;
  }

  @Provides @Singleton ImgurService provideSuperFanService() {
    return ImgurService.Factory.makeImugurService();
  }
}
