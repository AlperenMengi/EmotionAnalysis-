package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Models.QuestionModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityHopelessBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

import java.util.ArrayList;
import java.util.List;

public class HopelessActivity extends AppCompatActivity {

    private ActivityHopelessBinding binding;
    private List<QuestionModel> questionModelList;
    private QuestionModel currentQuestion1;

    int currentQuestion = 0;
    boolean isClickButton = false;
    String valueChoose = "";
    private Button lastClickedButton = null; // Son tıklanan butonun referansını saklar
    private ArrayList<String> choosenAnswersList;
    private ArrayList<String> choosenOptionList;
    String hopeless = "hopeless";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHopelessBinding.inflate(getLayoutInflater());
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
                Intent intent = new Intent(this,LastPageActivity.class);
                intent.putExtra("test", hopeless);
                intent.putStringArrayListExtra("answers", choosenAnswersList);
                intent.putStringArrayListExtra("options", choosenOptionList);
                startActivity(intent);
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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
        questionModelList.add(new QuestionModel("Geleceğe umut ve coşku ile bakıyorum.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Kendim ile ilgili şeyleri düzeltemediğime göre çabalamayı bıraksam iyi olur.","Evet","Hayır"));
        questionModelList.add(new QuestionModel(" İşler kötüye giderken bile her şeyin hep böyle kalmayacağını bilmek beni rahatlatıyor.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Gelecek on yıl içinde hayatimin nasıl olacağını hayal bile edemiyorum.","Evet","Hayır"));
        /*questionModelList.add(new QuestionModel("Yapmayı en çok istediğim şeyleri gerçekleştirmek için yeterli zamanım var.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Benim için çok önemli konularda ileride basarili olacağımı umuyorum.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Geleceğimi karanlık görüyorum.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Dünya nimetlerinden sıradan bir insandan daha çok yararlanacağımı umuyorum.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("İyi fırsatlar yakalayamıyorum. Gelecekte yakalayacağıma inanmam için de hiç bir neden yok.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Geçmiş deneyimlerim beni geleceğe iyi hazırladı.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Gelecek benim için hoş şeylerden çok tatsızlıklarla dolu görünüyor.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Gerçekten özlediğim şeylere kavuşabileceğimi ummuyorum.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Geleceğe baktığımda şimdikine oranla daha mutlu olacağımı umuyorum.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("İşler bir turlu benim istediğim gibi gitmiyor.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Geleceğe büyük inancım var.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Arzu ettiğim şeyleri elde edemediğime göre bir şeyler istemek aptallık olur.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Gelecekte gerçek doyuma ulaşmam olanaksız gibi.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Gelecek bana bulanık ve belirsiz görünüyor.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("Kotu günlerden çok , iyi günler bekliyorum.","Evet","Hayır"));
        questionModelList.add(new QuestionModel("İstediğim her şeyi elde etmek için caba göstermenin gerçekten yararı yok,nasıl olsa onu elde edemeyeceğim.","Evet","Hayır"));*/
    }
}