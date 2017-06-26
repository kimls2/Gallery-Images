package com.sample.ykim.galleyimages.injection.component;

import android.content.Context;
import com.sample.ykim.galleyimages.GalleyImagesApplication;
import com.sample.ykim.galleyimages.data.DataManager;
import com.sample.ykim.galleyimages.injection.ApplicationContext;
import com.sample.ykim.galleyimages.injection.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by ykim on 2017. 4. 11..
 */

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent {

  void inject(GalleyImagesApplication galleyImagesApplication);

  @ApplicationContext Context context();

  DataManager dataManager();
}
