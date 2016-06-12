package com.rafael.alexandre.alves.gistlist.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.rafael.alexandre.alves.gistlist.api.BaseAPI;
import com.rafael.alexandre.alves.gistlist.api.RestAPI;
import com.rafael.alexandre.alves.gistlist.model.Files;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.Owner;
import com.rafael.alexandre.alves.gistlist.ui.detail.GistDetailActivity;
import com.rafael.alexandre.alves.gistlist.utils.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Call;
import retrofit.Retrofit;

public class GistController extends BaseAPI {

    @SuppressLint("DefaultLocale")
    public Call<List<Gist>> getGistsCall(int page) throws IOException, JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(super.getBaseAPI())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI service = retrofit.create(RestAPI.class);

        return service.getGistList(page);
    }

    public Call<Gist> getGistByIDCall(String gistID) throws IOException, JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(super.getBaseAPI())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI service = retrofit.create(RestAPI.class);

        return service.getGistByID(gistID);
    }

    public void openGistDetail(Context context, Gist gist) {
        Intent intent = new Intent(context, GistDetailActivity.class);
        String ownerLogin = "";
        if (gist.owner != null) {
            ownerLogin = gist.owner.login;
        }
        intent.putExtra(Owner.OWNER_LOGIN, Utils.checkText(ownerLogin));

        intent.putExtra(Gist.GIST_ID, getGistIDByURL(gist.url));
        context.startActivity(intent);
    }

    public String getGistIDByURL(String url) {
        String[] gistArray = url.split("/gists/");

        return gistArray[gistArray.length - 1];
    }

    public Files getFilesFromMap(HashMap<String, Files> filesMap) {
        Files files = null;
        if (filesMap != null) {
            Map.Entry<String, Files> mapEntry = filesMap.entrySet().iterator().next();
            files = mapEntry.getValue();
        }

        return files;
    }
}
