package com.alperenmengi.duyguanaliziprototip.Models;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("_id")
    public String id;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserModel(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
