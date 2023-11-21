package com.alperenmengi.duyguanaliziprototip.Models;


import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
