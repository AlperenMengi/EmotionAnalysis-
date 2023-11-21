package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Models.QuestionModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private ActivityQuestionsBinding binding;
    private List<QuestionModel> questionModelList;
    private QuestionModel currentQuestion1;

    int currentQuestion = 0;
    boolean isClickButton = false;
    String valueChoose = "";
    private Button lastClickedButton = null; // Son tıklanan butonun referansını saklar
    private List<String> choosenAnswersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        choosenAnswersList = new ArrayList<>();
        questionModelList = new ArrayList<>();
        addQuestions();
        soruCevaplar();

        /*Intent intent = new Intent(QuestionsActivity.this, LastPageActivity.class);
        intent.putExtra("answers", new ArrayList<>(choosenAnswersList));
        startActivity(intent);*/
    }
    //Sonraki tuşuna basıldığında
    public void next(View view) {

        if(isClickButton){
            currentQuestion += 1; // soru indexini arttırma

            if(currentQuestion == (questionModelList.size())){ // son soruya gelinmişse teşekkür ekranına git.
                Intent intent = new Intent(this,LastPageActivity.class);
                intent.putStringArrayListExtra("answers", new ArrayList<>(choosenAnswersList));
                startActivity(intent);
            }
            else{//son soru değilse diğer soruları getir
                soruCevaplar(); // diğer soru ve cevapları yüklemek için
                isClickButton = false; // seçilen cevap olmadığını belirtmek için
                valueChoose = "";
                binding.choose1.setBackgroundResource(R.drawable.background_btn_choose);
                binding.choose2.setBackgroundResource(R.drawable.background_btn_choose);
                binding.choose3.setBackgroundResource(R.drawable.background_btn_choose);
                binding.choose4.setBackgroundResource(R.drawable.background_btn_choose);
            }
        }
        else
            Toast.makeText(this, "Lütfen bir cevap seçiniz!", Toast.LENGTH_LONG).show();

    }

    public void soruCevaplar(){
        binding.countQues.setText((currentQuestion + 1) + "/" + (questionModelList.size()));
        currentQuestion1 = questionModelList.get(currentQuestion);
        binding.textQuestion.setText(currentQuestion1.getQuestions());
        binding.choose1.setText(currentQuestion1.getAnswer1());
        binding.choose2.setText(currentQuestion1.getAnswer2());
        binding.choose3.setText(currentQuestion1.getAnswer3());
        binding.choose4.setText(currentQuestion1.getAnswer4());
    }

    private void addQuestions() {
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Kendimi üzgün hissetmiyorum", "Kendimi üzgün hissediyorum.", "Kendimi sürekli üzgün hissediyorum ve bundan kurtulamıyorum", "O kadar üzgün ve mutsuzum ki artık katlanamıyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Geleceğe karşı umutsuz ve karamsar değilim.", "Geleceğe dair karamsarım.", "Gelecekten beklediğim hiçbir şey yok.", "Geleceğim hakkında umutsuzum ve sanki hiçbir şey yoluna girmeyecekmiş gibi geliyor."));
    }

    // Anasayfaya geri döndürme butonu
    public void back(View View){
        Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
        startActivity(intent);

    }

    // seçeneklerden birine tıklandığında
    public void clickChoose(View view) {
        Button btn_click = (Button) view;
        if(!isClickButton){// herhangi bir butona basılmazsa
            btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
            lastClickedButton = btn_click;
            isClickButton = true;
            valueChoose = btn_click.getText().toString();
        }
        if(isClickButton){//bir butona basılırsa
            lastClickedButton.setBackgroundResource(R.drawable.background_btn_choose);// son butonun rengini düzelt.
            lastClickedButton = btn_click;// yeni basılan butonu son butona aktar.
            btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);// yeni basılan butonun rengini değiştir.

        }
        choosenAnswersList.add(lastClickedButton.getText().toString());


    }
}
/*
*   choosenAnswersList.add(lastClickedButton.getText().toString());
        Toast.makeText(this, choosenAnswersList.size(), Toast.LENGTH_LONG).show()
* */