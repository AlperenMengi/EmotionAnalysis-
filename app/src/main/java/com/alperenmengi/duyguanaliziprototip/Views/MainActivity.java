package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        // bu kısmı çalıştırırsam, lastPageActivity ya da AnswersActivity'den MainActivitye dönmeye çalıştığımda
        //hata alıyorum. her seferinde emaili çekmemi istiyor
       /*Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();*/

    }

    public void quit(View view) {
        this.finishAffinity(); // uygulamayı kapa
    }

    public void hopelessTest(View view) {
        Intent intent = new Intent(MainActivity.this, HopelessActivity.class);
        startActivity(intent);
    }

    public void depressionTest(View view) {
        Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
        startActivity(intent);
    }
}