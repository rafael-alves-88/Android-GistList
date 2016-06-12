package com.rafael.alexandre.alves.gistlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable {

    public static final String OWNER_LOGIN = "OwnerLogin";
    public static final String OWNER_GUID_OFFLINE = "d7b3f387-7453-4c5b-9783-b1e9c745d412.gist";

    @SerializedName("login")
    @Expose
    public String login;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;
    @SerializedName("name")
    @Expose
    public String name;
}
