package com.rafael.alexandre.alves.gistlist.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rafael.alexandre.alves.gistlist.R;
import com.rafael.alexandre.alves.gistlist.adapter.GistAdapter;
import com.rafael.alexandre.alves.gistlist.controller.GistController;
import com.rafael.alexandre.alves.gistlist.model.Gist;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GistController mGistController = new GistController();
    private List<Gist> mGistList;
    private GistAdapter mGistAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mCurrentPage = 0;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.rvGist) RecyclerView rvGist;
    @Bind(R.id.srlGist) SwipeRefreshLayout srlGist;
    @Bind(R.id.rlLoading) RelativeLayout rlLoading;
    @Bind(R.id.rlLoadingBottom) RelativeLayout rlLoadingBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setDrawer();
        getGists();
    }

    private void setDrawer() {
        if (drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void getGists() {
        try {
            if (mCurrentPage == 0) {
                rlLoading.setVisibility(View.VISIBLE);
            }
            Call<List<Gist>> call = mGistController.getGistsCall(mCurrentPage);

            call.enqueue(new Callback<List<Gist>>() {
                @Override
                public void onResponse(Response<List<Gist>> response, Retrofit retrofit) {
                    try {
                        List<Gist> loadedGistList = response.body();

                        if (loadedGistList != null && loadedGistList.size() > 0) {
                            if (mCurrentPage == 0) {
                                mGistList = loadedGistList;
                                mGistAdapter = new GistAdapter(MainActivity.this, mGistList, new GistAdapter.OnGistClickListener() {
                                    @Override
                                    public void onGistClick(Gist item) {
                                        mGistController.openGistDetail(MainActivity.this, item);
                                    }
                                });

                                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                                rvGist.setLayoutManager(mLayoutManager);
                                rvGist.setAdapter(mGistAdapter);

                                srlGist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {
                                        mCurrentPage = 0;
                                        getGists();
                                        srlGist.setRefreshing(false);
                                    }
                                });

                                rvGist.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);

                                        int visibleItemCount = mLayoutManager.getChildCount();
                                        int totalItemCount = mLayoutManager.getItemCount();
                                        int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                            mCurrentPage = mCurrentPage + 1;
                                            rlLoadingBottom.setVisibility(View.VISIBLE);
                                            getGists();
                                        }
                                    }
                                });
                            } else {
                                mGistList.addAll(loadedGistList);
                                mGistAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, R.string.error_empty_gist, Toast.LENGTH_SHORT).show();
                        }

                        rlLoading.setVisibility(View.GONE);
                        if (mCurrentPage > 0) {
                            dismissLoadingBottom();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    rlLoading.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, R.string.error_gist_information, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void dismissLoadingBottom() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rlLoadingBottom.setVisibility(View.GONE);
            }
        }, 1500);
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }
}
