package com.rafael.alexandre.alves.gistlist.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rafael.alexandre.alves.gistlist.R;
import com.rafael.alexandre.alves.gistlist.controller.GistController;
import com.rafael.alexandre.alves.gistlist.controller.OwnerController;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.Owner;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GistDetailActivity extends AppCompatActivity {

    private OwnerController mOwnerController = new OwnerController();
    private Owner mOwner;
    private GistController mGistController = new GistController();
    private Gist mFullGist;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.rlLoading) RelativeLayout rlLoading;
    @Bind(R.id.ivImage) ImageView ivImage;
    @Bind(R.id.tvLogin) TextView tvLogin;
    @Bind(R.id.tvName) TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gits_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                String ownerUrl = bundle.getString(Owner.OWNER_LOGIN);
                getOwnerInfo(ownerUrl);

                String gistUrl = bundle.getString(Gist.GIST_ID);
                getGistFullInfo(gistUrl);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getOwnerInfo(String login) throws IOException, JSONException {
        rlLoading.setVisibility(View.VISIBLE);
        Call<Owner> call = mOwnerController.getOwnerCall(login);

        call.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Response<Owner> response, Retrofit retrofit) {
                try {
                    mOwner = response.body();
                    setOwner(mOwner);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                rlLoading.setVisibility(View.GONE);
                Toast.makeText(GistDetailActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOwner(Owner owner) {
        Picasso.with(this).load(owner.avatarUrl).into(ivImage);
        tvLogin.setText(owner.login);
        tvName.setText(owner.name);
    }

    private void getGistFullInfo(String gistID) throws IOException, JSONException {
        rlLoading.setVisibility(View.VISIBLE);
        Call<Gist> call = mGistController.getGistByIDCall(gistID);

        call.enqueue(new Callback<Gist>() {
            @Override
            public void onResponse(Response<Gist> response, Retrofit retrofit) {
                try {
                    mFullGist = response.body();
                    setFullGist(mFullGist);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                rlLoading.setVisibility(View.GONE);
                Toast.makeText(GistDetailActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFullGist(Gist gist) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
