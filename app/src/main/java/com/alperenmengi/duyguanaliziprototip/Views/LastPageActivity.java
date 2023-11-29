package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alperenmengi.duyguanaliziprototip.R;

import java.util.ArrayList;
import java.util.List;

public class LastPageActivity extends AppCompatActivity {
    private List<String> answersList2;
    TextView resultText;
    TextView healthText;
    int countA = 0;
    int countB = 0;
    int countC = 0;
    int countD = 0;
    int pointA = 0;
    int pointB = 1;
    int pointC = 2;
    int pointD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_page);
        resultText = findViewById(R.id.resultText);
        healthText = findViewById(R.id.healthText);

        List<String> answersList = getIntent().getStringArrayListExtra("answers");
        answersList2 = answersList;

        List<String> optionsList = getIntent().getStringArrayListExtra("options");
        int point = sonucHesapla(optionsList);

        resultText.setText("Sonucunuz : " + point);

        if (point >= 0 && point <=9)
            healthText.setText("Minimal derecede deprosyona sahipsiniz.");
        else if (point >= 10 && point <=16)
            healthText.setText("Hafif derecede deprosyona sahipsiniz.");
        else if (point >= 17 && point <=29)
            healthText.setText("Orta derecede deprosyona sahipsiniz.");
        else if (point >= 30 && point <=63)
            healthText.setText("şiddetli derecede deprosyona sahipsiniz.");

    }
    public int sonucHesapla(List<String> optionsList){
        for (String option : optionsList){
            if (option.equals("A"))
                countA+=1;
            if (option.equals("B"))
                countB++;
            if (option.equals("C"))
                countC++;
            if (option.equals("D"))
                countD++;
        }
        return ((pointA*countA) +(pointB*countB)+(pointC*countC)+(pointD*countD));
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