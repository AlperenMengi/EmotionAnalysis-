package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.DialogInterface;
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
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityMainBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    //TextView textEvaluate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        //textEvaluate = findViewById(R.id.textEvaluate);

        /*textEvaluate.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EvaluateScreen.class);
            startActivity(intent);
        });*/

    }

   /*public void evaluateScreen(View view){
        Intent intent = new Intent(MainActivity.this, EvaluateScreen.class);
        startActivity(intent);
    }*/

    public void quit(View view) {
        this.finishAffinity(); // uygulamayı kapa
    }



    public void hopelessTest(View view) {
        alertDialog(HopelessActivity.class);
    }

    public void depressionTest(View view) {
        alertDialog(QuestionsActivity.class);
    }

    public void rosenbergD1Test(View view) {
        alertDialog(RosenbergD1Activity.class);
    }

    public void rosenbergD7Test(View view) {
        alertDialog(RosenbergD7Activity.class);
    }

    public void anxietyTest(View view) {
        alertDialog(AnxietyActivity.class);
    }

    public void wellbeingTest(View view) {
        alertDialog(WellBeingActivity.class);
    }

    public void evaluateScreen(View view){
        Intent intent = new Intent(MainActivity.this, EvaluateScreen.class);
        startActivity(intent);
    }
    public void alertDialog(Class activity){
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Teste başladığınızda soruları sadece 1 kez cevaplama hakkınız bulunmaktadır. " +
                "Lütfen soruları dikkatlice cevaplayınız.\n\nDevam etmek için İleri'ye tıklayınız.");
        builder.setTitle("Hatırlatma!");
        builder.setCancelable(false);//başka bir yere basılınca dialog'un kapanmasını önlüyor.
        builder.setPositiveButton("İleri", (DialogInterface.OnClickListener) (dialog, which) -> {
            Intent intent = new Intent(MainActivity.this, activity);
            startActivity(intent);
        });
        builder.setNegativeButton("İptal", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}