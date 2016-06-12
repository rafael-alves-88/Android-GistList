package com.rafael.alexandre.alves.gistlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangeStatus implements Serializable {

    @SerializedName("total")
    @Expose
    public int total;
    @SerializedName("additions")
    @Expose
    public int additions;
    @SerializedName("deletions")
    @Expose
    public int deletions;
}
