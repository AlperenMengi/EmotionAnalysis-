package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Models.QuestionModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

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
    private ArrayList<String> choosenAnswersList;
    private ArrayList<String> choosenOptionList;
    String depression = "depression";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
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
                Intent intent = new Intent(this,LastPageActivity.class);
                intent.putExtra("test", depression);
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
            Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
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
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Kendimi üzgün hissetmiyorum.", "Kendimi üzgün hissediyorum.", "Kendimi sürekli üzgün hissediyorum ve bundan kurtulamıyorum.", "O kadar üzgün ve mutsuzum ki artık katlanamıyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Geleceğe karşı umutsuz ve karamsar değilim.", "Geleceğe dair karamsarım.", "Gelecekten beklediğim hiçbir şey yok.", "Geleceğim hakkında umutsuzum ve sanki hiçbir şey yoluna girmeyecekmiş gibi geliyor."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Kendimi başarısız biri olarak tanımlamam.", "Kendimi ortalama bir insandan daha başarısız biri olarak tanımlarım.", "Geçmişim başarısılıklarla doluymuş gibi hissediyorum.", "Tümüyle başarısız biri olduğumu düşünüyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Her şeyden eskisi kadar zevk alabiliyorum.", "Birçok şeyden eskisi kadar zevk alamıyorum.", "Artık hiçbir şey bana keyif vermiyor.", "Her şeyden sıkılıyorum ve hiçbir şeyden memnun değilim."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Kendimi herhangi bir şekilde suçlu hissetmiyorum.", "Kendimi zaman zaman suçlu hissediyorum.", "Kendimi çoğu zaman oldukça suçlu hissediyorum", "Kendimi her zaman oldukça suçlu hissediyorum."));
        /*questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Cezalandırıldığımı düşünmüyorum.", "Cezalandırılabileceğimi hissediyorum.", "Cezalandırılmayı bekliyorum.", "Cezalandırıldığımı düşünüyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Kendimden memnunum.", "Kendimden pek memnum değilim.", "Kendimden tiksiniyorum.", "Kendimden nefret ediyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Başkalarından daha kötü olduğumu düşünmüyorum.", "Hatalarım ve zayıflıklarım için kendimi eleştiririm.", "Hatalarım için her zaman kendimi suçlarım.", "Başıma gelen her kötü şey için kendimi suçlarım."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Kendimi öldürmek gibi bir düşüncem yok.", "Kimi zaman kendimi öldürmeyi düşündüğüm oluyor, ama bunu yapmayacağım.", "Kendimi öldürmek isterdim.", "Fırsatını bulsaydımkendimi öldürürdüm."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Normalden daha fazla ağlama isteğim bulunmuyor.", "Artık eskisinden daha çok ağlıyorum.", "Sürekli ağlıyorum.", "Eskiden ağlayabilirdim ama şimdi içimden nedense ağlamak gelse de ağlayamıyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Her zamankinden daha fazla sinirlenmiyorum.", "Her zamankinden biraz daha sinirliyim.", "Çoğu zaman oldukça sinirliyim.", "Her zaman çok sinirliyim."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "İnsanlara duyduğum ilgide bir değişiklik yok.", "İnsanlarla eskisinden daha az ilgileniyorum.", "İnsanlara duydyğum ilgiyi büyük ölçüde kaybettim.", "İnsanlara karşı tüm ilgimi kaybettim."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Kara verirken güçlük çekmiyorum.", "Eskiden olduğu kadar kolay karar veremiyorum.", "Kara verme konusunda eskiye göre daha fazla güçlük çekiyorum.", "Artık hiçbir konuda karar veremiyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Eskisinden daha kötü göründüğümü düşünmüyorum.", "Eskiye kıyasla yaşlı ve çirkin göründüğümden endişe ediyorum.", "Görünüşümde yaşlı ve çirkin görünmeme neden olan kalıcı değişikliker olduğunu hissediyorum.", "Kendimi çok çirkin buluyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Eskisi kadar iyi iş yapabiliyorum.", "Her zaman yaptığım işler şimdilerde fazladan efor gerektiriyor.", "Ufak bir işi bile kendimi çok zorlayarak yapabiliyorum.", "Artık hiçbir iş yapamıyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Uyku düzenimde eskiye kıyasla bir değişiklik yok.", "Eskisi gibi uyuyamıyorum.", "Eskisine göre 1-2 saat erken uyanıyorum ve tekrar uykuya dalmakta zorlamıyorum.", "Eskisine göre çok erken uyanıyorum ve bir daha uyuyamıyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Her zamankinden daha çok yorulmuyorum.", "Her zamankine kıyasladaha çabuk yoruluyorum.", "Yaptığım her şeyde yorgunluk hissediyorum.", "Kendimi neredeyse hiçbir şey yapamayacak kadar yorgun hissediyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "İştahımda bir değişiklik yok.", "Son zamanlarda iştahım çok azaldı.", "Son zamanalarda iştahım çok azaldı.", "Artık hiç iştahım yok, hiçbir şey yiyemiyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Son zamanalarda kilo vermedim.", "Zayıflamaya çalışmadığım halde en az 2 kilo kaybettim.", "Zayıflamaya çalışmadığım halde en az 4 kilo kaybettim.", "Zayıflamaya çalışmadığım halde en az 6 kilo kaybettim."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Sağlığım hakkında fazla endişem yok.", "Ağrı, sancı, mide bozukluğu, veya kabızlık gibi rahıtsızlıklar beni endişelendiriyor.", "Sağlığımın bozulmasından endişeleniyorum ve bu sebeple kafamı başka şeylere vermekte zorlanıyorum.", "Sağlığım hakkında o kadar endişeliyim ki başka hiçbir şey düşünemiyorum."));*/
        //questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Sekse karşı ilgimde herhangi bir değişiklik yok.", "Eskisine göre sekse olan ilgim biraz azaldı.", "Sekse olan ilgim büyük ölçüde azaldı.", "Sekse olan ilgimi tamamen kaybettim."));
    }


}