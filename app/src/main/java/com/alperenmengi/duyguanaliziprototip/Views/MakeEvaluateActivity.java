package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;


import com.alperenmengi.duyguanaliziprototip.Models.EvaluateModel;
import com.alperenmengi.duyguanaliziprototip.Models.EvaluateUserModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Service.FeedbackAPI;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityEvaluatesBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityLoginBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityMakeEvaluateBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeEvaluateActivity extends AppCompatActivity {

    private ActivityMakeEvaluateBinding binding;

    String userID;
    Retrofit retrofit;
    private String BASE_URL = "https://emotionapi.onrender.com/";
    int rating = 0;
    EvaluateModel evaluateModel;
    ArrayList<EvaluateModel> evaluateModels;
    EvaluateUserModel evaluateUserModel;
    public ArrayList<String> commentArrayList;
    public ArrayList<Integer> ratingArrayList;
    public ArrayList<String> nameArrayList;
    String whichTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeEvaluateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        evaluateModels = new ArrayList<>();
        evaluateUserModel = new EvaluateUserModel();
        commentArrayList = new ArrayList<>();
        ratingArrayList = new ArrayList<>();
        nameArrayList = new ArrayList<>();
        whichTest = getIntent().getStringExtra("whichTest");

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = (int) ratingBar.getRating();
            }
        });

        LoginActivity loginActivity = new LoginActivity();
        userID = loginActivity.userID;
        System.out.println("MakeEvaluateActivity userID : "+ userID);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)// buradan bir json verisi alacağımızı söylüyoruz.
                .addConverterFactory(GsonConverterFactory.create(gson))// gelen JSON'ı modele göre alacağımıız retrofite de bidirmek için bu kısmı yazıyoruz
                .build();

        binding.sendEvaluateButton.setOnClickListener(view1 -> requestData());
        //binding.seeEvaulateButton.setOnClickListener(view2 -> loadData());

    }

    public void requestData(){
        if (!(binding.editTextTextMultiLine.getText().toString()).equals("")){
            evaluateModel = new EvaluateModel(userID, rating, binding.editTextTextMultiLine.getText().toString());
            System.out.println("request data içi userID : " + userID);
        }
        else
            Toast.makeText(this, "Lütfen bir değerlendirme giriniz!", Toast.LENGTH_SHORT).show();

        FeedbackAPI feedbackAPI = retrofit.create(FeedbackAPI.class);

        Call<EvaluateModel> call = feedbackAPI.sendData(evaluateModel);
        call.enqueue(new Callback<EvaluateModel>() {
            @Override
            public void onResponse(Call<EvaluateModel> call, Response<EvaluateModel> response) {
                if (response.isSuccessful())
                    Toast.makeText(MakeEvaluateActivity.this, "Değerlendirmeniz Başarıyla Kaydedildi.", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(MakeEvaluateActivity.this, LastPageActivity.class);
                    intent.putExtra("returnWhichTest", whichTest);
                    startActivity(intent);*/
            }
            @Override
            public void onFailure(Call<EvaluateModel> call, Throwable t) {
                Toast.makeText(MakeEvaluateActivity.this, "Değerlendirme yollanamadı!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    /*public void loadData(){
        FeedbackAPI feedbackAPI1 = retrofit.create(FeedbackAPI.class);
        Call<List<EvaluateModel>> call = feedbackAPI1.getData();

        call.enqueue(new Callback<List<EvaluateModel>>() {
            @Override
            public void onResponse(Call<List<EvaluateModel>> call, Response<List<EvaluateModel>> response) {

                List<EvaluateModel> responseList = response.body();
                evaluateModels = new ArrayList<>(responseList);
                for (EvaluateModel evaluate : evaluateModels){
                    if (evaluate.user!=null){
                        System.out.println(evaluate.user);
                        System.out.println(evaluate.user.name);
                        nameArrayList.add(evaluate.user.name);
                    }
                    System.out.println(evaluate.comment);
                    System.out.println(evaluate.rating);
                    System.out.println("-----------------");
                    commentArrayList.add(evaluate.comment);
                    ratingArrayList.add(evaluate.rating);

                    Intent intent = new Intent(MakeEvaluateActivity.this, LastPageActivity.class);
                    intent.putExtra("returnWhichTest", whichTest);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<List<EvaluateModel>> call, Throwable t) {
                Toast.makeText(MakeEvaluateActivity.this, "Değerlendirmeler görüntülenemedi!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }*/





    /*
    public void sendToAPI(View view) {
        System.out.println(userID);
        Toast.makeText(this, "Değerlendirmeniz başarıyla gönderildi.", Toast.LENGTH_SHORT).show();
    }*/
}