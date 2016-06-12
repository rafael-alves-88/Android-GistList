package com.rafael.alexandre.alves.gistlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class History implements Serializable {

    @SerializedName("user")
    @Expose
    public Owner user;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("committed_at")
    @Expose
    public String committed_at;
    @SerializedName("change_status")
    @Expose
    public ChangeStatus change_status;
}
