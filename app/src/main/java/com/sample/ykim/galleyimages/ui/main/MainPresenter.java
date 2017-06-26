package com.sample.ykim.galleyimages.ui.main;

import com.sample.ykim.galleyimages.data.DataManager;
import com.sample.ykim.galleyimages.data.model.GalleryImage;
import com.sample.ykim.galleyimages.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by ykim on 2017. 4. 11..
 */

public class MainPresenter extends BasePresenter<MainMvpView> {

  private final DataManager mDataManager;
  private final CompositeDisposable mCompositeDisposable;

  @Inject public MainPresenter(DataManager dataManager) {
    this.mDataManager = dataManager;
    mCompositeDisposable = new CompositeDisposable();
  }

  @Override public void detachView() {
    super.detachView();
    mCompositeDisposable.clear();
  }

  public void loadGallery(boolean pullToRefresh, String section, String sort, String window,
      int page, boolean showViral) {
    if (!pullToRefresh) {
      getMvpView().showLoading(true);
    }

    mCompositeDisposable.add(mDataManager.getGallery(section, sort, window, page, showViral)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableObserver<List<GalleryImage>>() {
          @Override public void onNext(List<GalleryImage> galleryImages) {
            getMvpView().showImages(galleryImages);
          }

          @Override public void onError(Throwable e) {
            getMvpView().showLoading(false);
            getMvpView().showErrorMessage(e.getMessage());
          }

          @Override public void onComplete() {
            getMvpView().showLoading(false);
          }
        }));
  }
}
