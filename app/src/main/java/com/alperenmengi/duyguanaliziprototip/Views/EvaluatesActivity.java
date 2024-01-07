package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Adapter.EvaluatesAdapter;
import com.alperenmengi.duyguanaliziprototip.Models.EvaluateModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Service.FeedbackAPI;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityEvaluatesBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityLastPageBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluatesActivity extends AppCompatActivity {
    private ActivityEvaluatesBinding binding;

    private String BASE_URL = "https://emotionapi.onrender.com/";
    Retrofit retrofit;

    public ArrayList<String> commentArrayList;
    public ArrayList<Integer> ratingArrayList;
    public ArrayList<String> nameArrayList;
    public ArrayList<String> feedbackArrayList;
    ArrayList<EvaluateModel> evaluateModels;

    EvaluatesAdapter evaluatesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEvaluatesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        commentArrayList = new ArrayList<>();
        ratingArrayList = new ArrayList<>();
        nameArrayList = new ArrayList<>();
        feedbackArrayList = new ArrayList<>();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }


    public void loadData(){
        FeedbackAPI feedbackAPI1 = retrofit.create(FeedbackAPI.class);
        Call<List<EvaluateModel>> call = feedbackAPI1.getData();

        call.enqueue(new Callback<List<EvaluateModel>>() {
            @Override
            public void onResponse(Call<List<EvaluateModel>> call, Response<List<EvaluateModel>> response) {

                List<EvaluateModel> responseList = response.body();
                evaluateModels = new ArrayList<>(responseList);
                for (EvaluateModel evaluate : evaluateModels){
                    if (evaluate.user!=null){
                        nameArrayList.add(evaluate.user.name);
                        System.out.println(evaluate.user.name);
                        System.out.println(evaluate.feedbackID);
                    }
                    commentArrayList.add(evaluate.comment);
                    System.out.println(evaluate.comment);
                    ratingArrayList.add(evaluate.rating);
                    System.out.println(evaluate.rating);
                    feedbackArrayList.add(evaluate.feedbackID);

                    //Adapter ile activitenin bağlandığı kısım
                    binding.recyclerView2.setLayoutManager(new LinearLayoutManager(EvaluatesActivity.this));
                    evaluatesAdapter = new EvaluatesAdapter(nameArrayList, ratingArrayList, commentArrayList, feedbackArrayList);
                    binding.recyclerView2.setAdapter(evaluatesAdapter);


                }
            }
            @Override
            public void onFailure(Call<List<EvaluateModel>> call, Throwable t) {
                Toast.makeText(EvaluatesActivity.this, "Değerlendirmeler görüntülenemedi!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }


}