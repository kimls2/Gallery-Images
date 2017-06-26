package com.sample.ykim.galleyimages.ui.about;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sample.ykim.galleyimages.R;
import com.sample.ykim.galleyimages.ui.base.BaseActivity;

/**
 * Created by ykim on 2017. 4. 11..
 */

public class AboutActivity extends BaseActivity {

  @BindView(R.id.app_version_tv) TextView appVersionTv;
  @BindView(R.id.development_time_tv) TextView developmentTimeTv;

  @SuppressLint("SetTextI18n") @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    String appVersion = "";
    try {
      PackageInfo packageInfo = getApplicationContext().getPackageManager()
          .getPackageInfo(getApplicationContext().getPackageName(), 0);
      appVersion = packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    appVersionTv.setText(appVersion);
    developmentTimeTv.setText("11.04.2017 ~ 17.04.2017");
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
