package com.sample.ykim.galleyimages.ui.base;

/**
 * Created by ykim on 2017. 4. 11..
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
