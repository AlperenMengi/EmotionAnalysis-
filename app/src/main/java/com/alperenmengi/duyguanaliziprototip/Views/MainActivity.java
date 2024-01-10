package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Tests.AnxietyActivity;
import com.alperenmengi.duyguanaliziprototip.Tests.HopelessActivity;
import com.alperenmengi.duyguanaliziprototip.Tests.QuestionsActivity;
import com.alperenmengi.duyguanaliziprototip.Tests.RosenbergD1Activity;
import com.alperenmengi.duyguanaliziprototip.Tests.RosenbergD7Activity;
import com.alperenmengi.duyguanaliziprototip.Tests.WellBeingActivity;

public class MainActivity extends AppCompatActivity {

    TextView textEvaluate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        textEvaluate = findViewById(R.id.textEvaluate);

        // bu kısmı çalıştırırsam, lastPageActivity ya da AnswersActivity'den MainActivitye dönmeye çalıştığımda
        //hata alıyorum. her seferinde emaili çekmemi istiyor
       /*Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();*/

        textEvaluate.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EvaluateScreen.class);
            startActivity(intent);
        });

    }

   /*public void evaluateScreen(View view){
        Intent intent = new Intent(MainActivity.this, EvaluateScreen.class);
        startActivity(intent);
    }*/

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

    public void rosenbergD1Test(View view) {
        Intent intent = new Intent(MainActivity.this, RosenbergD1Activity.class);
        startActivity(intent);
    }

    public void rosenbergD7Test(View view) {
        Intent intent = new Intent(MainActivity.this, RosenbergD7Activity.class);
        startActivity(intent);
    }

    public void anxietyTest(View view) {
        Intent intent = new Intent(MainActivity.this, AnxietyActivity.class);
        startActivity(intent);
    }

    public void wellbeingTest(View view) {
        Intent intent = new Intent(MainActivity.this, WellBeingActivity.class);
        startActivity(intent);
    }
}