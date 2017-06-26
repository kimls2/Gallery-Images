package com.sample.ykim.galleyimages.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sample.ykim.galleyimages.R;
import com.sample.ykim.galleyimages.data.model.GalleryImage;
import com.sample.ykim.galleyimages.ui.about.AboutActivity;
import com.sample.ykim.galleyimages.ui.base.BaseActivity;
import com.sample.ykim.galleyimages.ui.detail.DetailActivity;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by ykim on 2017. 4. 11..
 */

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
    NavigationView.OnNavigationItemSelectedListener, MainMvpView, AdapterItemCallback {

  // section type
  private static final String SECTION_HOT = "hot";
  private static final String SECTION_TOP = "top";
  private static final String SECTION_USER = "user";
  // show viral
  private static final String VIRAL_INCLUDE = "include";
  // sort type;
  private static final String SORT_VIRAL = "viral";
  private static final String SORT_TOP = "top";
  private static final String SORT_TIME = "time";
  private static final String SORT_RISING = "rising";
  // window type
  private static final String WINDOW_DAY = "day";
  private static final String WINDOW_WEEK = "week";
  private static final String WINDOW_MONTH = "month";
  private static final String WINDOW_YEAR = "year";
  private static final String WINDOW_ALL = "all";

  @Inject protected MainPresenter mMainPresenter;
  private MainAdapter mMainAdapter;

  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.main_rv) RecyclerView mainRv;
  @BindView(R.id.nav_view) NavigationView navView;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.progressBar) ProgressBar mProgressBar;
  @BindView(R.id.fab) FloatingActionButton mFab;

  private boolean loadMore = false;
  private int currentPage = 1;
  private String sectionType = SECTION_HOT;
  private String sortType = SORT_VIRAL;
  private String windowType = WINDOW_DAY;
  private boolean showViral = true;
  private boolean userIsInteracting;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    activityComponent().inject(this);
    ButterKnife.bind(this);

    mSwipeRefreshLayout.setOnRefreshListener(this);
    mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
    setSupportActionBar(toolbar);
    mFab.setOnClickListener(v -> mainRv.smoothScrollToPosition(0));

    // drawer
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    // navigation view
    navView.setNavigationItemSelectedListener(this);
    Menu navigationMenu = navView.getMenu();
    initializeSectionSpinner(navigationMenu);
    initializeShowViralSpinner(navigationMenu);
    initializeSortSpinner(navigationMenu);
    initializeWindowSpinner(navigationMenu);
    initializeLayoutStyleSpinner(navigationMenu);

    // recyclerView
    setLoadMorePageScroll();
    mMainAdapter = new MainAdapter(this);
    mainRv.setLayoutManager(new GridLayoutManager(this, 2));
    mainRv.setAdapter(mMainAdapter);

    // presenter
    mMainPresenter.attachView(this);
    loadGallery(false);
  }

  @Override public void onUserInteraction() {
    super.onUserInteraction();
    userIsInteracting = true;
  }

  @Override public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mMainPresenter.detachView();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_about) {
      startActivity(new Intent(MainActivity.this, AboutActivity.class));
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return false;
  }

  /***** MVP View methods implementation *****/
  @Override public void showLoading(boolean show) {
    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void showErrorMessage(String message) {
    Snackbar.make(drawerLayout, message, Snackbar.LENGTH_LONG) //
        .setAction(R.string.snack_bar_action_retry, v -> loadGallery(false))  //
        .show();
  }

  @Override public void showImages(List<GalleryImage> images) {
    if (loadMore) {
      mMainAdapter.addData(images);
      loadMore = false;
    } else {
      mainRv.scrollToPosition(0);
      mMainAdapter.setData(images);
    }
  }

  /***** SwipeRefreshLayout *****/
  @Override public void onRefresh() {
    loadGallery(true);
  }

  /***** Adapter callback *****/
  @Override public void itemOnClicked(GalleryImage galleryImage) {
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
    intent.putExtra(DetailActivity.IMAGE_DETAIL, galleryImage);
    startActivity(intent);
  }

  private void loadGallery(boolean pullToRefresh) {
    if (!loadMore) currentPage = 1;
    mMainPresenter.loadGallery(pullToRefresh, sectionType, sortType, windowType, currentPage,
        showViral);
  }

  private void initializeSpinner(int menuItemId, Menu navigationMenu, int arrayResourceId,
      AdapterView.OnItemSelectedListener listener) {
    Spinner spinner = (Spinner) MenuItemCompat.getActionView(navigationMenu.findItem(menuItemId));
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, arrayResourceId, R.layout.spinner_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(listener);
  }

  private void initializeSectionSpinner(Menu navigationMenu) {
    initializeSpinner(R.id.menu_item_section, navigationMenu, R.array.array_section,
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            navView.getMenu().findItem(R.id.menu_item_window).setVisible(false);
            String selected = parent.getItemAtPosition(position).toString();
            switch (selected) {
              case SECTION_HOT:
                sectionType = SECTION_HOT;
                break;
              case SECTION_TOP:
                // only top section shows window menu
                navView.getMenu().findItem(R.id.menu_item_window).setVisible(true);
                sectionType = SECTION_TOP;
                break;
              case SECTION_USER:
                sectionType = SECTION_USER;
                break;
            }

            if (userIsInteracting) {
              loadGallery(false);
            }
            drawerLayout.closeDrawers();
          }

          @Override public void onNothingSelected(AdapterView<?> parent) {
          }
        });
  }

  private void initializeShowViralSpinner(Menu navigationMenu) {
    initializeSpinner(R.id.menu_item_show_viral, navigationMenu, R.array.array_show_viral,
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
            showViral = selected.equals(VIRAL_INCLUDE);
            if (userIsInteracting) {
              loadGallery(false);
            }
            drawerLayout.closeDrawers();
          }

          @Override public void onNothingSelected(AdapterView<?> parent) {

          }
        });
  }

  private void initializeSortSpinner(Menu navigationMenu) {
    initializeSpinner(R.id.menu_item_sort, navigationMenu, R.array.array_sort,
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
            switch (selected) {
              case SORT_VIRAL:
                sortType = SORT_VIRAL;
                break;
              case SORT_TOP:
                sortType = SORT_TOP;
                break;
              case SORT_TIME:
                sortType = SORT_TIME;
                break;
              case SORT_RISING:
                sortType = SORT_RISING;
                break;
            }
            if (userIsInteracting) {
              loadGallery(false);
            }
            drawerLayout.closeDrawers();
          }

          @Override public void onNothingSelected(AdapterView<?> parent) {

          }
        });
  }

  private void initializeWindowSpinner(Menu navigationMenu) {
    initializeSpinner(R.id.menu_item_window, navigationMenu, R.array.array_window,
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
            switch (selected) {
              case WINDOW_DAY:
                windowType = WINDOW_DAY;
                break;
              case WINDOW_WEEK:
                windowType = WINDOW_WEEK;
                break;
              case WINDOW_MONTH:
                windowType = WINDOW_MONTH;
                break;
              case WINDOW_YEAR:
                windowType = WINDOW_YEAR;
                break;
              case WINDOW_ALL:
                windowType = WINDOW_ALL;
                break;
            }
            if (userIsInteracting) {
              loadGallery(false);
            }
            drawerLayout.closeDrawers();
          }

          @Override public void onNothingSelected(AdapterView<?> parent) {

          }
        });
  }

  private void initializeLayoutStyleSpinner(Menu navigationMenu) {
    initializeSpinner(R.id.menu_item_layout_style, navigationMenu, R.array.array_layout_style,
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
            if (selected.equals(getString(R.string.layout_list))) {
              mainRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
              mainRv.setAdapter(mMainAdapter);
              mMainAdapter.setLayout(false);
            } else if (selected.equals(getString(R.string.layout_grid))) {
              mainRv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
              mainRv.setAdapter(mMainAdapter);
              mMainAdapter.setLayout(true);
            } else if (selected.equals(getString(R.string.layout_staggered_grid))) {
              mainRv.setLayoutManager(
                  new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
              mainRv.setAdapter(mMainAdapter);
              mMainAdapter.setLayout(false);
            }
            drawerLayout.closeDrawers();
          }

          @Override public void onNothingSelected(AdapterView<?> parent) {
          }
        });
  }

  private void setLoadMorePageScroll() {
    mainRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0 || dy < 0 && mFab.isShown()) {
          mFab.hide();
        }

        if (!recyclerView.canScrollVertically(1)) {
          currentPage++;
          loadMore = true;
          loadGallery(false);
        }
      }

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          mFab.show();
        }
        super.onScrollStateChanged(recyclerView, newState);
      }
    });
  }
}
