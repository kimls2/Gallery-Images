package com.sample.ykim.galleyimages.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sample.ykim.galleyimages.R;
import com.sample.ykim.galleyimages.data.model.GalleryImage;
import com.sample.ykim.galleyimages.ui.base.BaseActivity;
import com.sample.ykim.galleyimages.util.ImageUtil;

/**
 * Created by ykim on 2017. 4. 12..
 */

public class DetailActivity extends BaseActivity {

  public static final String IMAGE_DETAIL = "image_detail";

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.big_thumbnail_iv) ImageView mBigThumbnailIv;
  @BindView(R.id.title_tv) TextView mTitleTv;
  @BindView(R.id.description_tv) TextView mDescriptionTv;
  @BindView(R.id.up_vote_tv) TextView mUpVoteTv;
  @BindView(R.id.down_vote_tv) TextView mDownVoteTv;
  @BindView(R.id.score_tv) TextView mScoreTv;
  @BindView(R.id.progressBar) View progressBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    ButterKnife.bind(this);

    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Bundle extras = getIntent().getExtras();
    if (extras != null && extras.getSerializable(IMAGE_DETAIL) != null) {
      GalleryImage galleryImage = (GalleryImage) extras.getSerializable(IMAGE_DETAIL);
      assert galleryImage != null;
      ImageUtil.loadImageWithProgress(mBigThumbnailIv, galleryImage.getDetailUrl(), progressBar);
      mTitleTv.setText(galleryImage.getTitle());
      mDescriptionTv.setText(galleryImage.getDescription());
      mUpVoteTv.setText(String.valueOf(galleryImage.getUps()));
      mDownVoteTv.setText(String.valueOf(galleryImage.getDowns()));
      mScoreTv.setText(String.valueOf(galleryImage.getScore()));
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
