package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alperenmengi.duyguanaliziprototip.R;

public class EvaluateScreen extends AppCompatActivity {
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_screen);

        back = findViewById(R.id.imageView3);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(EvaluateScreen.this, MainActivity.class);
            startActivity(intent);
            EvaluateScreen.this.finish();
        });
    }

    public void makeEvaluate(View view) {
        Intent intent = new Intent(EvaluateScreen.this, MakeEvaluateActivity.class);
        startActivity(intent);
    }

    public void seeEvaluate(View view) {
        Intent intent = new Intent(EvaluateScreen.this, EvaluatesActivity.class);
        startActivity(intent);
    }
}