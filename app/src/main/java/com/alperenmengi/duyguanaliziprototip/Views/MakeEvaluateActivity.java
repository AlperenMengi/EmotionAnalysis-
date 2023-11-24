package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.R;

public class MakeEvaluateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_evaluate);

    }

    public void sendToAPI(View view) {
        Toast.makeText(this, "Değerlendirmeniz başarıyla gönderildi.", Toast.LENGTH_SHORT).show();
    }
}