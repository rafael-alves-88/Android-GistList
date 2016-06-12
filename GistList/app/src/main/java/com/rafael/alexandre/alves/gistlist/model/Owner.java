package com.rafael.alexandre.alves.gistlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {

    public static final String OWNER_LOGIN = "OwnerLogin";

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
