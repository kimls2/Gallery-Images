package com.sample.ykim.galleyimages.ui.main;

import com.sample.ykim.galleyimages.data.model.GalleryImage;
import com.sample.ykim.galleyimages.ui.base.MvpView;
import java.util.List;

/**
 * Created by ykim on 2017. 4. 11..
 */

public interface MainMvpView extends MvpView {

  void showImages(List<GalleryImage> images);

}
