package com.sample.ykim.galleyimages.data;

import com.sample.ykim.galleyimages.data.model.GalleryImage;
import com.sample.ykim.galleyimages.data.model.GalleryResponse;
import com.sample.ykim.galleyimages.data.remote.ImgurService;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Created by ykim on 2017. 4. 11..
 */

@Singleton public class DataManager {

  private final ImgurService mImgurService;

  @Inject public DataManager(ImgurService mImgurService) {
    this.mImgurService = mImgurService;
  }

  public Observable<List<GalleryImage>> getGallery(String section, String sort, String window,
      int page, boolean showViral) {
    return mImgurService.getGallery(section, sort, window, page, showViral)
        .filter(response -> response.isSuccess() && CollectionUtils.isNotEmpty(response.getData()))
        .map(GalleryResponse::getData);
  }
}
