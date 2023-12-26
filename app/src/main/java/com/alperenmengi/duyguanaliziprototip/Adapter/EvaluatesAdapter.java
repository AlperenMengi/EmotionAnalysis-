package com.alperenmengi.duyguanaliziprototip.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alperenmengi.duyguanaliziprototip.databinding.EvaluateRowBinding;

import java.util.ArrayList;

public class EvaluatesAdapter extends RecyclerView.Adapter<EvaluatesAdapter.EvaluatesHolder> {

    private ArrayList<String> nameList;
    private ArrayList<Integer> ratingList;
    private ArrayList<String> commentList;

    public EvaluatesAdapter(ArrayList<String> nameList, ArrayList<Integer> ratingList, ArrayList<String> commentList) {
        this.nameList = nameList;
        this.ratingList = ratingList;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public EvaluatesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EvaluateRowBinding evaluateRowBinding = EvaluateRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EvaluatesHolder(evaluateRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluatesHolder holder, int position) {
        holder.binding.nameText.setText("Kullanıcı : " + nameList.get(position));
        holder.binding.ratingText.setText("Puan : " + ratingList.get(position).toString()+ "/5");
        holder.binding.commentText.setText("Yorum : " + commentList.get(position));

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


    public class EvaluatesHolder extends RecyclerView.ViewHolder {

        private EvaluateRowBinding binding;

        public EvaluatesHolder(EvaluateRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
