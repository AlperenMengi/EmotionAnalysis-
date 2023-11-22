package com.alperenmengi.duyguanaliziprototip.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alperenmengi.duyguanaliziprototip.Views.AnswersActivity;
import com.alperenmengi.duyguanaliziprototip.databinding.RecyclerRowBinding;

import java.util.ArrayList;
import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersHolder> {

    List<String> answersActivityArrayList;

    public AnswersAdapter(List<String> answersActivityArrayList) {
        this.answersActivityArrayList = answersActivityArrayList;
    }

    @NonNull
    @Override
    public AnswersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AnswersHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersHolder holder, int position) {
        holder.binding.recyclerViewTextView.setText((position + 1) + " - "+  answersActivityArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return answersActivityArrayList.size();
    }

    public class AnswersHolder extends RecyclerView.ViewHolder{

        private RecyclerRowBinding binding;
        public AnswersHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
