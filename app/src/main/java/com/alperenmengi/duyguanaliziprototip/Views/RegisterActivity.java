package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Models.UserModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Service.UserAPI;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityLoginBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityRegisterBinding;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {


    private String BASE_URL = "https://emotionapi.onrender.com/";
    Retrofit retrofit;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.kayitOlButton.setOnClickListener(view1 -> {
            if ((!binding.sifre2Text.getText().toString().isEmpty()) && !(binding.sifreText.getText().toString().equals(binding.sifre2Text.getText().toString()))){
                System.out.println("ife girmedi");
                Toast.makeText(this, "Şifreler Uyuşmadı!", Toast.LENGTH_SHORT).show();
                binding.kayitOlButton.setBackgroundResource(R.drawable.background_password);
            }
            else{
                binding.kayitOlButton.setBackgroundResource(R.drawable.background_btn_sent);
                requestData();
            }
        });
    }

    public void requestData(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)// buradan bir json verisi alacağımızı söylüyoruz.
                .addConverterFactory(GsonConverterFactory.create())// gelen JSON'ı modele göre alacağımıız retrofite de bidirmek için bu kısmı yazıyoruz
                .build();

        UserModel userModel = new UserModel(binding.isimText.getText().toString().trim(), binding.epostaText.getText().toString().trim(), binding.sifreText.getText().toString().trim());
        UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<UserModel> call = userAPI.sendUser(userModel);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Kullanıcı Başarlı Bir Şekilde Oluşturuldu.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Kullanıcı Oluşturulamadı.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }


}