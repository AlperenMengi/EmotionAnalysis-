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

    public EvaluateModel(String feedbackID, int rating, String comment) {
        this.feedbackID = feedbackID;
        this.rating = rating;
        this.comment = comment;
    }
}


/*public String getEvaluateUsername(){
        return evaluateUsername.name;
    }*/
//EvaluateUserModel evaluateUserModel = new EvaluateUserModel();

/*
    public EvaluateUserModel getEvaluateUserModel(){
        return evaluateUserModel;
    }

//name, user isimli bir objenin içindeydi. Bende objeyi taklit etmek için bir sınıf oluşturup içine name koydum, oradaki name'yi çektim.
    public String getName() {
        return (evaluateUserModel != null) ? evaluateUserModel.name : "Bilinmeyen";
    }*/