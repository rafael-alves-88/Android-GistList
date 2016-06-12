package com.rafael.alexandre.alves.gistlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Gist {

    public static final String GIST_ID = "GistID";
    public static final String CONTENT = "Content";
    public static final String HISTORY = "History";
    public static final String HISTORY_LIST = "HistoryList";

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("files")
    @Expose
    public HashMap<String, Files> files;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("owner")
    @Expose
    public Owner owner;
    @SerializedName("history")
    @Expose
    public List<History> history;
}