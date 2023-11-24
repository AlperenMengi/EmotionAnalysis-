package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alperenmengi.duyguanaliziprototip.Adapter.AnswersAdapter;
import com.alperenmengi.duyguanaliziprototip.R;

import java.util.ArrayList;
import java.util.List;

public class LastPageActivity extends AppCompatActivity {
    private List<String> answersList2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_page);

        List<String> answersList = getIntent().getStringArrayListExtra("answers");
        answersList2 = answersList;
    }

    public void backToTheFirstPage(View view) {
        Intent intent = new Intent(LastPageActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void seeAnswers(View view) {
        Intent intent = new Intent(LastPageActivity.this, AnswersActivity.class);
        intent.putStringArrayListExtra("answers", new ArrayList<>(answersList2));
        startActivity(intent);
    }

    public void seeAllEvaluate(View view) {
        Intent intent = new Intent(LastPageActivity.this, EvaluatesActivity.class);
        startActivity(intent);
    }

    public void makeEvaluate(View view) {
        Intent intent = new Intent(LastPageActivity.this, MakeEvaluateActivity.class);
        startActivity(intent);
    }


}