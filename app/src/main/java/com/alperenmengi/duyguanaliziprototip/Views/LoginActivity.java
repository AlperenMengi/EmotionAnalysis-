package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Models.UserModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Service.UserAPI;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityLoginBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    ArrayList<UserModel> userModels;
    private String BASE_URL = "https://emotionapi.onrender.com/";
    Retrofit retrofit;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ConstraintLayout constraintLayout = findViewById(R.id.loginLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)// buradan bir json verisi alacağımızı söylüyoruz.
                .addConverterFactory(GsonConverterFactory.create(gson))// gelen JSON'ı modele göre alacağımıız retrofite de bidirmek için bu kısmı yazıyoruz
                .build();

        //loadData();
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestData();
                /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);*/
            }
        });
    }

    private void requestData(){
        UserModel userModel = new UserModel(binding.email.getText().toString().trim(), binding.password.getText().toString());
        UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<UserModel> call = userAPI.sendData(userModel);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //intent.putExtra("email", response.body().email);
                    startActivity(intent);
                }else
                    Toast.makeText(LoginActivity.this, "Yanlış Şifre veya E-posta Girdiniz!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Api'ya Erişilemedi", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    /*
    public void requestData(){
        UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<List<UserModel>> request = userAPI.sendData(binding.username.getText().toString(), binding.password.getText().toString());
        System.out.println(binding.username.getText());
        System.out.println(binding.password.getText());
        request.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()){
                    List<UserModel> responseList = response.body();
                    userModels = new ArrayList<>(responseList);
                    binding.loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(LoginActivity.this, userModels.get(0).password, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                System.out.println("hatalı erişim");
                Toast.makeText(LoginActivity.this, "api'ya erişemedin", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }*/


    public void loadData(){
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<UserModel>> call = userAPI.getData();

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()){
                    List<UserModel> responseList = response.body();
                    userModels = new ArrayList<>(responseList);
                    for (UserModel userModel : userModels){
                        System.out.println("şifreler : " + userModel.password);
                    }/*
                    binding.loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (UserModel usermodel : userModels){
                                System.out.println("API'daki Şifreler : " + usermodel.password);
                                if (usermodel.name.equals(binding.username.getText().toString()) && usermodel.password.equals(binding.password.getText().toString())){
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    });*/
                }

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                System.out.println("api ya bağlanılamadı");
                t.printStackTrace();
            }
        });
    }

}