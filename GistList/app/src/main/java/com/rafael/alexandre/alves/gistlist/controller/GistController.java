package com.rafael.alexandre.alves.gistlist.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.rafael.alexandre.alves.gistlist.api.BaseAPI;
import com.rafael.alexandre.alves.gistlist.api.RestAPI;
import com.rafael.alexandre.alves.gistlist.model.Files;
import com.rafael.alexandre.alves.gistlist.model.Gist;
import com.rafael.alexandre.alves.gistlist.model.Owner;
import com.rafael.alexandre.alves.gistlist.ui.about.AboutActivity;
import com.rafael.alexandre.alves.gistlist.ui.detail.GistDetailActivity;
import com.rafael.alexandre.alves.gistlist.utils.Serializer;
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

    @SuppressLint("DefaultLocale")
    public List<Gist> getGistOffline(Context context, int page) throws IOException, ClassNotFoundException {
        return (List<Gist>) new Serializer(context).Read(String.format("%d-%s", page, Gist.GIST_GUID_OFFLINE));
    }

    @SuppressLint("DefaultLocale")
    public void saveGistOffline(final Context context, final List<Gist> gistList, final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    new Serializer(context).Write(String.format("%d-%s", page, Gist.GIST_GUID_OFFLINE), gistList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
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

    public void openAboutActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
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
