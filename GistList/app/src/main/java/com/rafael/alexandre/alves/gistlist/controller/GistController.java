package com.rafael.alexandre.alves.gistlist.controller;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;

import com.rafael.alexandre.alves.gistlist.api.BaseAPI;
import com.rafael.alexandre.alves.gistlist.api.RestAPI;
import com.rafael.alexandre.alves.gistlist.model.Gist;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

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

    public String getGistIDByURL(String url) {
        String[] gistArray = url.split("/gists/");

        return gistArray[gistArray.length - 1];
    }
}
