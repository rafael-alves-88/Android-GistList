package com.rafael.alexandre.alves.gistlist.controller;

import android.annotation.SuppressLint;

import com.rafael.alexandre.alves.gistlist.api.BaseAPI;
import com.rafael.alexandre.alves.gistlist.api.RestAPI;
import com.rafael.alexandre.alves.gistlist.model.Owner;

import org.json.JSONException;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class OwnerController extends BaseAPI {

    @SuppressLint("DefaultLocale")
    public Call<Owner> getOwnerCall(String login) throws IOException, JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(super.getBaseAPI())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI service = retrofit.create(RestAPI.class);

        return service.getOwner(login);
    }
}
