package com.alperenmengi.duyguanaliziprototip.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EvaluateModel {
    @SerializedName("_id")
    public String feedbackID;
    @SerializedName("rating")
    public int rating;
    @SerializedName("comment")
    public String comment;
    @SerializedName("user")
    public EvaluateUserModel user;

    public EvaluateModel() {
        // Boş constructor
    }
    public EvaluateModel(String feedbackID, int rating, String comment) {
        this.feedbackID = feedbackID;
        this.rating = rating;
        this.comment = comment;
    }
}
