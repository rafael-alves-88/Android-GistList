package com.rafael.alexandre.alves.gistlist.api;

import com.rafael.alexandre.alves.gistlist.model.Gist;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

public interface RestAPI {

    @GET("/gists/public?page=0")
    Call<List<Gist>> getGistList();
}
