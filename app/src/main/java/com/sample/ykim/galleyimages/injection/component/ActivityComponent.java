package com.sample.ykim.galleyimages.injection.component;

import com.sample.ykim.galleyimages.injection.PerActivity;
import com.sample.ykim.galleyimages.injection.module.ActivityModule;
import com.sample.ykim.galleyimages.ui.about.AboutActivity;
import com.sample.ykim.galleyimages.ui.detail.DetailActivity;
import com.sample.ykim.galleyimages.ui.main.MainActivity;
import dagger.Component;

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(MainActivity mainActivity);

  void inject(AboutActivity aboutActivity);

  void inject(DetailActivity detailActivity);
}

