package com.rafael.alexandre.alves.gistlist.controller;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
        String url = String.format("%s%spublic?page=%d", super.getBaseAPI(), Gist.GISTS_URL, page);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI service = retrofit.create(RestAPI.class);

        Call<List<Gist>> call = service.getGistList();

        return call;
    }
}
