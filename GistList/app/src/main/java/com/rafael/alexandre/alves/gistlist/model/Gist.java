package com.rafael.alexandre.alves.gistlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Gist implements Serializable {

    public static final String GIST_ID = "GistID";
    public static final String CONTENT = "Content";
    public static final String HISTORY = "History";
    public static final String HISTORY_LIST = "HistoryList";
    public static final String GIST_GUID_OFFLINE = "154fc5c3-5ded-480f-825c-f44bea3fffd6.gist";
    public static final String GIST_FULL_GIST_GUID_OFFLINE = "560b4451-b590-4338-a7c0-e5bb57faee47.gist";

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