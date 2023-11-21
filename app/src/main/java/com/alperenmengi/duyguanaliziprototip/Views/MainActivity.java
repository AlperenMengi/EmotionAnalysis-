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

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show();
    }

    public void main_btn(View View){
        if (View.getId() == R.id.baslabutton){
            Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
            startActivity(intent);
        }
        if(View.getId() == R.id.cikisbutton){
            this.finishAffinity(); // uygulamayı kapa
        }
    }


}