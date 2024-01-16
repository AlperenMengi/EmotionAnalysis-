package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alperenmengi.duyguanaliziprototip.Camera.facialExpressionRecognition;
import com.alperenmengi.duyguanaliziprototip.Tests.QuestionsActivity;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityDetailResultBinding;


public class DetailResultActivity extends AppCompatActivity {

    private ActivityDetailResultBinding binding;

    int countMutlu;
    int countSinirli;
    int countSaskin;
    int countUzgun;
    int countNotr;
    int countIgrenmis;
    int countKorkmus;
    int countToplamDuygu;

    int pcountMutlu;
    int pcountSinirli;
    int pcountSaskin;
    int pcountUzgun;
    int pcountNotr;
    int pcountIgrenmis;
    int pcountKorkmus;

    LastPageActivity lastPageActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        lastPageActivity = new LastPageActivity();

        countMutlu = facialExpressionRecognition.countMutlu;
        System.out.println("countMutlu : " + countMutlu);
        countKorkmus = facialExpressionRecognition.countKorkmus;
        countNotr = facialExpressionRecognition.countNotr;
        System.out.println("coutnNotr : " + countNotr);
        countSaskin = facialExpressionRecognition.countSaskin;
        countSinirli = facialExpressionRecognition.countSinirli;
        countUzgun = facialExpressionRecognition.countUzgun;
        System.out.println("countUzgun : " + countUzgun);
        countIgrenmis = facialExpressionRecognition.countIgrenmıs;
        countToplamDuygu = countMutlu + countKorkmus + countNotr + countSaskin + countSinirli + countUzgun + countIgrenmis;
        System.out.println("countToplamDuygu : " + countToplamDuygu);

        pcountMutlu = (int) (( (double) countMutlu / countToplamDuygu) * 100);
        pcountSinirli = (int) (( (double) countSinirli / countToplamDuygu) * 100);
        pcountSaskin = (int) (( (double) countSaskin / countToplamDuygu) * 100);
        pcountUzgun = (int) (( (double) countUzgun / countToplamDuygu) * 100);
        pcountNotr = (int) (( (double) countNotr / countToplamDuygu) * 100);
        pcountIgrenmis = (int) (( (double) countIgrenmis / countToplamDuygu) * 100);
        pcountKorkmus = (int) (( (double) countKorkmus / countToplamDuygu) * 100);

        binding.pmutlu.setText("%" + pcountMutlu);
        binding.pigrenmis.setText("%" + pcountIgrenmis);
        binding.pkorkmus.setText("%" + pcountKorkmus);
        binding.pnotr.setText("%" + pcountNotr);
        binding.psaskin.setText("%" + pcountSaskin);
        binding.psinirli.setText("%" + pcountSinirli);
        binding.puzgun.setText("%" + pcountUzgun);
        binding.yapayZekaSonuc.setText(lastPageActivity.yzSonucu + " puan");
        binding.testSonucu.setText(lastPageActivity.testSonucu + " puan");
        binding.genelSonuc.setText(lastPageActivity.genelSonuc + " puan");
    }

}