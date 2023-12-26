package com.alperenmengi.duyguanaliziprototip.Service;

import com.alperenmengi.duyguanaliziprototip.Models.EvaluateModel;
import com.alperenmengi.duyguanaliziprototip.Models.UserModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FeedbackAPI {

    @POST("feedback/")
    Call<EvaluateModel> sendData(@Body EvaluateModel evaluateModel);

    @GET("feedback/")
    Call<List<EvaluateModel>> getData();
}
