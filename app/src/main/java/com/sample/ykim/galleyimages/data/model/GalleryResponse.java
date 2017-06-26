package com.sample.ykim.galleyimages.data.model;

import java.util.List;
import lombok.Data;

/**
 * Created by ykim on 2017. 4. 11..
 */

@Data public class GalleryResponse {
  private boolean success;
  private int status;
  private List<GalleryImage> data;
}
