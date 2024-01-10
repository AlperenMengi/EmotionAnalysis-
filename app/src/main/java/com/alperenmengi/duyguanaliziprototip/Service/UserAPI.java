package com.alperenmengi.duyguanaliziprototip.Service;

import com.alperenmengi.duyguanaliziprototip.Models.UserModel;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {

    @GET("user/")
    Call<List<UserModel>> getData();

    //@FormUrlEncoded
    //@POST("auth/login")
    //Call<List<UserModel>> sendData(@Field("name") String name, @Field("password") String password);

    @POST("auth/login")
    Call<UserModel> sendData(@Body UserModel userModel);

    @POST("auth/register")
    Call<UserModel> sendUser(@Body UserModel userModel);



}
