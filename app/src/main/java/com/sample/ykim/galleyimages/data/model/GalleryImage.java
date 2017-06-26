package com.sample.ykim.galleyimages.data.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by ykim on 2017. 4. 11..
 */

@Data @Accessors(chain = true) public class GalleryImage implements Serializable {

  private static final String BASE_IMAGE_ADDRESS = "http://i.imgur.com/";

  public static final char SMALL_SQUARE = 's';
  public static final char BIG_SQUARE = 'b';
  public static final char SMALL_THUMBNAIL = 't';
  public static final char MEDIUM_THUMBNAIL = 'm';
  public static final char LARGE_THUMBNAIL = 'l';
  public static final char HUGE_THUMBNAIL = 'h';

  private String id;
  private String title;
  private String description;
  private String type;
  private int width;
  private int height;
  private int size;
  private String section;
  private int ups;
  private int downs;
  private int score;
  private String link;
  private String cover;
  private String gifv;
  private boolean animated;

  // album
  private int cover_width;
  private boolean is_album;
  private int cover_height;
  private List<GalleryImage> images;

  public float getAspectRation() {
    float minAspectRatio = 0.7f;
    if (is_album()) {
      return Math.max(minAspectRatio, cover_width / cover_height);
    } else {
      return Math.max(minAspectRatio, width / height);
    }
  }

  public String getThumbnailSize(char size) {
    String thumbnailId = id;
    if (is_album) {
      thumbnailId = cover;
    }
    return BASE_IMAGE_ADDRESS + thumbnailId + size + ".jpg";
  }

  public String getDetailUrl() {
    String url = getThumbnailSize(HUGE_THUMBNAIL);
    // gif image / less than 20MB
    if (isAnimated() && size < 21000000) {
      url = link;
    }
    return url;
  }
}
