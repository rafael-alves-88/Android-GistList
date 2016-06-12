package com.rafael.alexandre.alves.gistlist.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.rafael.alexandre.alves.gistlist.api.BaseAPI;
import com.rafael.alexandre.alves.gistlist.api.RestAPI;
import com.rafael.alexandre.alves.gistlist.model.Owner;
import com.rafael.alexandre.alves.gistlist.utils.Serializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

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

    @SuppressLint("DefaultLocale")
    public Owner getOwnerOffline(Context context, String login) throws IOException, ClassNotFoundException {
        return (Owner) new Serializer(context).Read(String.format("%s-%s", login.replace("/", ""), Owner.OWNER_GUID_OFFLINE));
    }

    @SuppressLint("DefaultLocale")
    public void saveOwnerOffline(final Context context, final Owner owner, final String login) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    new Serializer(context).Write(String.format("%s-%s", login.replace("/", ""), Owner.OWNER_GUID_OFFLINE), owner);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
