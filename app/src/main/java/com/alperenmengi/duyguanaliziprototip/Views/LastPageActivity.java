package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.R;

import java.util.ArrayList;
import java.util.List;

public class LastPageActivity extends AppCompatActivity {
    private ArrayList<String> answersListDepression;
    private ArrayList<String> answersListHopeless;
    private ArrayList<String> optionsListDepression;
    private ArrayList<String> optionsListHopeless;
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
    String whichTest;
    int pointHopeless = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_page);
        resultText = findViewById(R.id.resultText);
        healthText = findViewById(R.id.healthText);

        whichTest = getIntent().getStringExtra("test"); // hangi testten gelindiğini belirledik
        Toast.makeText(this, whichTest, Toast.LENGTH_SHORT).show();
        if (whichTest.equals("hopeless")){
            answersListHopeless = getIntent().getStringArrayListExtra("answers");
            optionsListHopeless = getIntent().getStringArrayListExtra("options");
            for (i = 0; i < optionsListHopeless.size(); i++){
                if (i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 12 || i == 14 || i == 18){
                    if (optionsListHopeless.get(i).equals("B"))
                        pointHopeless++;
                }
                else{
                    if (optionsListHopeless.get(i).equals("A"))
                        pointHopeless++;
                }
            }
            resultText.setText("Sonucunuz : " + pointHopeless + " puan");
            print(pointHopeless, "Umutsuzluk");
        }

        if (whichTest.equals("depression")){
            answersListDepression = getIntent().getStringArrayListExtra("answers");
            optionsListDepression = getIntent().getStringArrayListExtra("options");

            int pointDepression = sonucHesapla(optionsListDepression);
            resultText.setText("Sonucunuz : " + pointDepression + " puan");
            print(pointDepression, "Depresyon");

        }
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
    public void print(int point, String test){
        if (point >= 0 && point <=9)
            healthText.setText("Minimal Derecede "+ test + " Sahibisiniz.");
        else if (point >= 10 && point <=16)
            healthText.setText("Hafif Derecede "+ test + " Sahibisiniz.");
        else if (point >= 17 && point <=29)
            healthText.setText("Orta Derecede "+ test + " Sahibisiniz.");
        else if (point >= 30 && point <=63)
            healthText.setText("Şiddetli Derecede "+ test + " Sahibisiniz.");
    }

    public void backToTheFirstPage(View view) {
        Intent intent = new Intent(LastPageActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void seeAnswers(View view) {
        Intent intent = new Intent(LastPageActivity.this, AnswersActivity.class);
        if (whichTest.equals("depression"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListDepression));
        if (whichTest.equals("hopeless"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListHopeless));
        startActivity(intent);

    }

    public void seeAllEvaluate(View view) {
        Toast.makeText(this, "Değerlendirme sayfasına gidilecek", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(LastPageActivity.this, EvaluatesActivity.class);
        //startActivity(intent);
    }

    public void makeEvaluate(View view) {
        Intent intent = new Intent(LastPageActivity.this, MakeEvaluateActivity.class);
        startActivity(intent);
    }


}