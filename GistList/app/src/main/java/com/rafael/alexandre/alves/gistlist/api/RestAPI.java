package com.rafael.alexandre.alves.gistlist.api;

import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.Owner;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RestAPI {

    @GET("/gists/public")
    Call<List<Gist>> getGistList(@Query("page") int page);

    @GET("/gists/{gistID}")
    Call<Gist> getGistByID(@Path("gistID") String gistID);

    @GET("/users/{login}")
    Call<Owner> getOwner(@Path("login") String login);
}
