package com.rafael.alexandre.alves.gistlist.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

    private GistController gistController = new GistController();
    private List<Gist> mGistList;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.rvGist) RecyclerView rvGist;
    @Bind(R.id.rlLoading) RelativeLayout rlLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        try {
            getGists(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void getGists(final int page) throws IOException, JSONException {
        rlLoading.setVisibility(View.VISIBLE);
        Call<List<Gist>> call = gistController.getGistsCall(page);

        call.enqueue(new Callback<List<Gist>>() {
            @Override
            public void onResponse(Response<List<Gist>> response, Retrofit retrofit) {
                try {
                    mGistList = response.body();

                    GistAdapter adapter = new GistAdapter(MainActivity.this, mGistList, new GistAdapter.OnGistClickListener() {
                        @Override
                        public void onGistClick(Gist item) {
                            Toast.makeText(MainActivity.this, item.url, Toast.LENGTH_SHORT).show();
                        }
                    });

                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                    rvGist.setLayoutManager(mLayoutManager);
                    rvGist.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                rlLoading.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
