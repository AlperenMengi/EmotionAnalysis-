package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alperenmengi.duyguanaliziprototip.Adapter.AnswersAdapter;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityAnswersBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

import java.util.ArrayList;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {

    private ActivityAnswersBinding binding;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnswersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        List<String> answersList= getIntent().getStringArrayListExtra("answers");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AnswersAdapter answersAdapter = new AnswersAdapter(answersList);
        binding.recyclerView.setAdapter(answersAdapter);


    }

    public void backToFirstPage(View view) {
        Intent intent = new Intent(AnswersActivity.this, MainActivity.class);
        startActivity(intent);
    }
}