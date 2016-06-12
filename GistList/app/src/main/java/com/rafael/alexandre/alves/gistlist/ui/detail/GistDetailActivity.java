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
import com.rafael.alexandre.alves.gistlist.controller.GistDetailController;
import com.rafael.alexandre.alves.gistlist.controller.OwnerController;
import com.rafael.alexandre.alves.gistlist.model.AppMode;
import com.rafael.alexandre.alves.gistlist.model.Files;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.Owner;
import com.rafael.alexandre.alves.gistlist.ui.MainActivity;
import com.rafael.alexandre.alves.gistlist.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GistDetailActivity extends AppCompatActivity {

    private OwnerController mOwnerController = new OwnerController();
    private Owner mOwner;
    private GistController mGistController = new GistController();
    private Gist mFullGist;
    private GistDetailController mGistDetailController = new GistDetailController();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.rlLoading) RelativeLayout rlLoading;
    @Bind(R.id.ivImage) ImageView ivImage;
    @Bind(R.id.tvLogin) TextView tvLogin;
    @Bind(R.id.tvName) TextView tvName;
    @Bind(R.id.tvFileName) TextView tvFileName;
    @Bind(R.id.tvFileType) TextView tvFileType;
    @Bind(R.id.tvFileLanguage) TextView tvFileLanguage;
    @Bind(R.id.tvCreatedAt) TextView tvCreatedAt;
    @Bind(R.id.tvUpdatedAt) TextView tvUpdatedAt;
    @Bind(R.id.tvDescription) TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gist_detail_activity);
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

        if (MainActivity.mAppMode == AppMode.ONLINE) {
            getOnlineOwnerInfo(login);
        } else {
            getOfflineOwnerInfo(login);
        }
    }

    private void getOnlineOwnerInfo(final String login) {
        try {
            Call<Owner> call = mOwnerController.getOwnerCall(login);

            call.enqueue(new Callback<Owner>() {
                @Override
                public void onResponse(Response<Owner> response, Retrofit retrofit) {
                    try {
                        mOwner = response.body();
                        mOwnerController.saveOwnerOffline(GistDetailActivity.this, mOwner, login);
                        setOwner(mOwner);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    rlLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Throwable t) {
                    rlLoading.setVisibility(View.GONE);
                    Toast.makeText(GistDetailActivity.this, R.string.error_owner_data, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException | JSONException e) {
            rlLoading.setVisibility(View.GONE);
            Toast.makeText(GistDetailActivity.this, R.string.error_owner_data, Toast.LENGTH_SHORT).show();
        }
    }

    private void getOfflineOwnerInfo(String login) {
        try {
            mOwner = mOwnerController.getOwnerOffline(this, login);
            setOwner(mOwner);
        } catch (IOException | ClassNotFoundException e) {
            rlLoading.setVisibility(View.GONE);
        }
    }

    private void setOwner(Owner owner) {
        if (owner != null) {
            Picasso.with(this).load(owner.avatarUrl).into(ivImage);
            tvLogin.setText(Utils.checkText(owner.login));
            tvName.setText(Utils.checkText(owner.name));
        }
    }

    private void getGistFullInfo(String gistID) throws IOException, JSONException {
        rlLoading.setVisibility(View.VISIBLE);

        if (MainActivity.mAppMode == AppMode.ONLINE) {
            getOnlineFullGist(gistID);
        } else {
            getOfflineFullGist(gistID);
        }
    }

    private void getOnlineFullGist(final String gistId) {
        try {
            Call<Gist> call = mGistController.getGistByIDCall(gistId);

            call.enqueue(new Callback<Gist>() {
                @Override
                public void onResponse(Response<Gist> response, Retrofit retrofit) {
                    try {
                        mFullGist = response.body();
                        mGistDetailController.saveFullGistOffline(GistDetailActivity.this, mFullGist, gistId);
                        setFullGist(mFullGist);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    rlLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Throwable t) {
                    rlLoading.setVisibility(View.GONE);
                    Toast.makeText(GistDetailActivity.this, R.string.error_gist_data, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException | JSONException e) {
            rlLoading.setVisibility(View.GONE);
            Toast.makeText(GistDetailActivity.this, R.string.error_gist_data, Toast.LENGTH_SHORT).show();
        }
    }

    private void getOfflineFullGist(String gistId) {
        try {
            mFullGist = mGistDetailController.getFullGistOffline(this, gistId);
            setFullGist(mFullGist);
        } catch (IOException | ClassNotFoundException e) {
            rlLoading.setVisibility(View.GONE);
        }
    }

    private void setFullGist(Gist gist) {
        if (gist.files != null) {
            Files files = mGistController.getFilesFromMap(gist.files);
            tvFileName.setText(Utils.checkText(files.filename));
            tvFileType.setText(Utils.checkText(files.type));
            tvFileLanguage.setText(Utils.checkText(files.language));
        }

        tvCreatedAt.setText(Utils.checkText(gist.createdAt));
        tvUpdatedAt.setText(Utils.checkText(gist.updatedAt));
        tvDescription.setText(Utils.checkText(gist.description));

        rlLoading.setVisibility(View.GONE);
    }

    @OnClick(R.id.fabContent)
    public void fabContentClick() {
        if (mFullGist.files != null && mFullGist.files.size() > 0) {
            mGistDetailController.openContentDialog(mFullGist, getSupportFragmentManager());
        } else {
            Toast.makeText(this, R.string.error_empty_gist, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fabHistory)
    public void fabHistoryClick() {
        if (mFullGist.history != null && mFullGist.history.size() > 0) {
            mGistDetailController.openHistoryDialog(mFullGist, getSupportFragmentManager());
        } else {
            Toast.makeText(this, R.string.error_empty_history, Toast.LENGTH_SHORT).show();
        }
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
