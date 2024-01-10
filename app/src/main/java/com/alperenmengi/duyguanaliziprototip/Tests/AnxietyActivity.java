package com.alperenmengi.duyguanaliziprototip.Tests;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Models.QuestionModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Views.LastPageActivity;
import com.alperenmengi.duyguanaliziprototip.Views.MainActivity;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityAnxietyBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

import java.util.ArrayList;
import java.util.List;

public class AnxietyActivity extends AppCompatActivity {


    private ActivityAnxietyBinding binding;
    private List<QuestionModel> questionModelList;
    private QuestionModel currentQuestion1;

    int currentQuestion = 0;
    boolean isClickButton = false;
    String valueChoose = "";
    private Button lastClickedButton = null; // Son tıklanan butonun referansını saklar
    private ArrayList<String> choosenAnswersList;
    private ArrayList<String> choosenOptionList;
    String anxiety = "anxiety";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnxietyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        choosenAnswersList = new ArrayList<>();
        choosenOptionList = new ArrayList<>();
        questionModelList = new ArrayList<>();
        addQuestions();
        soruCevaplar();

    }


    //Soru ve cevapları gösterme
    public void soruCevaplar() {
        binding.countQues.setText((currentQuestion + 1) + "/" + (questionModelList.size()));
        currentQuestion1 = questionModelList.get(currentQuestion);
        binding.textQuestion.setText(currentQuestion1.getQuestions());
        binding.choose1.setText("A" + ") " + currentQuestion1.getAnswer1());
        binding.choose2.setText("B" + ") " + currentQuestion1.getAnswer2());
        binding.choose3.setText("C" + ") " + currentQuestion1.getAnswer3());
        binding.choose4.setText("D" + ") " + currentQuestion1.getAnswer4());

        isClickButton = false; // Her soruda başlangıçta isClickButton'ı false olarak ayarla

        for (String answer : choosenAnswersList) {
            if (answer.equals(binding.choose1.getText().toString())) {
                binding.choose1.setBackgroundResource(R.drawable.background_btn_choose_color);
                isClickButton = true;
            } else {
                binding.choose1.setBackgroundResource(R.drawable.background_btn_choose);
            }

            if (answer.equals(binding.choose2.getText().toString())) {
                binding.choose2.setBackgroundResource(R.drawable.background_btn_choose_color);
                isClickButton = true;
            } else {
                binding.choose2.setBackgroundResource(R.drawable.background_btn_choose);
            }

            if (answer.equals(binding.choose3.getText().toString())) {
                binding.choose3.setBackgroundResource(R.drawable.background_btn_choose_color);
                isClickButton = true;
            } else {
                binding.choose3.setBackgroundResource(R.drawable.background_btn_choose);
            }

            if (answer.equals(binding.choose4.getText().toString())) {
                binding.choose4.setBackgroundResource(R.drawable.background_btn_choose_color);
                isClickButton = true;
            } else {
                binding.choose4.setBackgroundResource(R.drawable.background_btn_choose);
            }
        }
    }

    //Sonraki tuşuna basıldığında
    public void next(View view) {
        if(isClickButton){
            String fullText = lastClickedButton.getText().toString();
            String[] fullAnswer = fullText.split("\\) ");
            choosenOptionList.add(fullAnswer[0].trim());
            choosenAnswersList.add(fullAnswer[1].trim());

            currentQuestion += 1; // soru indexini arttırma

            if (currentQuestion == (questionModelList.size() - 1))
                binding.nextButton.setText("SONUCUNU GÖR");

            if(currentQuestion == (questionModelList.size())){ // son soruya gelinmişse teşekkür ekranına git.
                Intent intent = new Intent(this, LastPageActivity.class);
                intent.putExtra("test", anxiety);
                intent.putStringArrayListExtra("answers", choosenAnswersList);
                intent.putStringArrayListExtra("options", choosenOptionList);
                startActivity(intent);
                AnxietyActivity.this.finish();
            }
            else{//son soru değilse diğer soruları getir
                soruCevaplar(); // diğer soru ve cevapları yüklemek için
                isClickButton = false; // seçilen cevap olmadığını belirtmek için
                valueChoose = "";
                /*binding.choose1.setBackgroundResource(R.drawable.background_btn_choose);
                binding.choose2.setBackgroundResource(R.drawable.background_btn_choose);
                binding.choose3.setBackgroundResource(R.drawable.background_btn_choose);
                binding.choose4.setBackgroundResource(R.drawable.background_btn_choose);*/
            }
        }
        else
            Toast.makeText(this, "Lütfen bir cevap seçiniz!", Toast.LENGTH_LONG).show();
    }

    // bir soru geri gelme butonu
    public void back(View View) {
        if (currentQuestion == 0) {
            Intent intent = new Intent(AnxietyActivity.this, MainActivity.class);
            startActivity(intent);
            AnxietyActivity.this.finish();
        } else {
            currentQuestion -= 1;
            // Print the selected answer for the current question
            if (!choosenAnswersList.isEmpty()) {
                String selectedAnswer = choosenAnswersList.get(currentQuestion);
                System.out.println("Selected answer for question " + (currentQuestion + 1) + ": " + selectedAnswer);
            }
            soruCevaplar(); // diğer soru ve cevapları yüklemek için
            isClickButton = false; // seçilen cevap olmadığını belirtmek için

/*
            binding.choose1.setBackgroundResource(R.drawable.background_btn_choose);
            binding.choose2.setBackgroundResource(R.drawable.background_btn_choose);
            binding.choose3.setBackgroundResource(R.drawable.background_btn_choose);
            binding.choose4.setBackgroundResource(R.drawable.background_btn_choose);*/
        }
    }

    // seçeneklerden birine tıklandığında
    public void clickChoose(View view) {
        Button btn_click = (Button) view;
        if(!isClickButton){// herhangi bir butona basılmazsa
            btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
            lastClickedButton = btn_click;
            isClickButton = true;
        }
        if(isClickButton){//bir butona basılırsa
            lastClickedButton.setBackgroundResource(R.drawable.background_btn_choose);// son butonun rengini düzelt.
            lastClickedButton = btn_click;// yeni basılan butonu son butona aktar.
            btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);// yeni basılan butonun rengini değiştir.
            valueChoose = btn_click.getText().toString();
        }
    }

    //Örnek soru ekleme
    private void addQuestions() {
        questionModelList.add(new QuestionModel("Bedeninin herhangi bir yerinde uyuşma veya karıncalanma", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Sıcak / Ateş Basması", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Bacaklarda titreme", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Gevşeyememe", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("En kötü şeylerin başına geleceği korkusu", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Baş dönmesi veya sersemlik", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Kalp çarpıntısı", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Denge kaybı", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Dehşete kapılma ya da korkma", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Sinirlilik", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Boğuluyormuş gibi hissetme", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Ellerinde titreme", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Titreklik", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Kontrolü kaybetme korkusu", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Nefes almakta zorlanma", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Ölüm korkusu", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Korkuya kapılma", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Midede hazımsızlık", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Bayılma ya da baygınlık", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Yüz kızarması", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
        questionModelList.add(new QuestionModel("Soğuk ya da sıcak terlemeler", "Hiç deneyimlemedim", "Hafif düzeyde. Beni pek etkilemedi", "Orta düzeyde. Hoş değildi ama katlanılabilirdi.", "Ciddi düzeyde. Dayanmakta çok zorlandım."));
 }


}