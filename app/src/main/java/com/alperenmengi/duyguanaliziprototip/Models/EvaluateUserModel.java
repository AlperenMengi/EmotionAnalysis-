package com.alperenmengi.duyguanaliziprototip.Models;

import com.google.gson.annotations.SerializedName;

public class EvaluateUserModel {

    @SerializedName("_id")
    public String id;

    @SerializedName("name")
    public String name;

    public EvaluateUserModel(){
        //boş constructor
    }
    public EvaluateUserModel(String id){
        this.id = id;
    }
}
