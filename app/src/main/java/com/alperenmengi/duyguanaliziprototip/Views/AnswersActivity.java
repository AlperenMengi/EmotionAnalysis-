package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Adapter.AnswersAdapter;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityAnswersBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

import java.util.ArrayList;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {

    List<String> answerList2;
    private ActivityAnswersBinding binding;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnswersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        List<String> answersList = getIntent().getStringArrayListExtra("answers");
        answerList2 = answersList;
        if (answerList2.size() == 0)
            Toast.makeText(this, "liste boş", Toast.LENGTH_SHORT).show();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AnswersAdapter answersAdapter = new AnswersAdapter(answerList2);
        binding.recyclerView.setAdapter(answersAdapter);

    }

    public void backToFirstPage(View view) {
        Intent intent = new Intent(AnswersActivity.this, MainActivity.class);
        startActivity(intent);
    }
}