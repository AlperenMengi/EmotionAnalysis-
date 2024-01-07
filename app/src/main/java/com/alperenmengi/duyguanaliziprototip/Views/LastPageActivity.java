package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityLastPageBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LastPageActivity extends AppCompatActivity {
    private ActivityLastPageBinding binding;

    private ArrayList<String> answersListDepression;
    private ArrayList<String> answersListHopeless;
    private ArrayList<String> answersListRosenbergD1;
    private ArrayList<String> answersListRosenbergD7;
    private ArrayList<String> optionsListDepression;
    private ArrayList<String> optionsListHopeless;
    private ArrayList<String> optionsListRosenbergD1;
    private ArrayList<String> optionsListRosenbergD7;

    float countA = 0;
    float countB = 0;
    float countC = 0;
    float countD = 0;
    float pointA = 0;
    float pointB = 1;
    float pointC = 2;
    float pointD = 3;
    float pointHopeless = 0;
    int i = 0;
    String whichTest;
    String whichTestMakeEvaluateActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLastPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        whichTest = getIntent().getStringExtra("test"); // hangi testten gelindiğini belirledik
        //whichTestMakeEvaluateActivity = getIntent().getStringExtra("returnWhichTest");
        //if içinde || whichTest2.equals yazarız.
        if(whichTest != null){
            if (whichTest.equals("hopeless")){
                answersListHopeless = getIntent().getStringArrayListExtra("answers");
                optionsListHopeless = getIntent().getStringArrayListExtra("options");

                pointHopeless = sonucHesaplaHopeless(optionsListHopeless);
                binding.resultText.setText("Sonucunuz : " + pointHopeless + "/20 puan");
                printHopeless(pointHopeless, "Umutsuzluk");
            }

            if (whichTest.equals("depression")){
                answersListDepression = getIntent().getStringArrayListExtra("answers");
                optionsListDepression = getIntent().getStringArrayListExtra("options");

                float pointDepression = sonucHesaplaDepression(optionsListDepression);
                binding.resultText.setText("Sonucunuz : " + pointDepression + "/60 puan");
                printDepression(pointDepression, "Depresyon");

            }
            if (whichTest.equals("rosenbergD1")){
                answersListRosenbergD1 = getIntent().getStringArrayListExtra("answers");
                optionsListRosenbergD1  = getIntent().getStringArrayListExtra("options");

                float pointRosenbergD1 = sonucHesaplaRosenbergD1(optionsListRosenbergD1);
                binding.resultText.setText("Sonucunuz : " + pointRosenbergD1 + "/6 puan");
                printRosenbergD1(pointRosenbergD1, "Benlik Saygısı'na");
            }
            if (whichTest.equals("rosenbergD7")){
                answersListRosenbergD7 = getIntent().getStringArrayListExtra("answers");
                optionsListRosenbergD7 =getIntent().getStringArrayListExtra("options");

                float pointRosenbergD7 = sonucHesaplaRosenbergD7(optionsListRosenbergD7);
                binding.resultText.setText("Sonucunuz : " + pointRosenbergD7 + "/10 puan");
                printRosenbergD7(pointRosenbergD7, "Psikosomatik Belirti'ye");
            }
        }

    }



    private float sonucHesaplaHopeless(ArrayList<String> optionsListHopeless) {
        for (i = 0; i < optionsListHopeless.size(); i++){
            if (i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || i == 12 || i == 14 || i == 18){
                if (optionsListHopeless.get(i).equals("B"))
                    pointHopeless++;
            }
            else{
                if (optionsListHopeless.get(i).equals("A"))
                    pointHopeless++;
            }
        }
        return pointHopeless;
    }

    private float sonucHesaplaRosenbergD7(List<String> optionsListRosenbergD7) {
        float RD7pointA=1;
        float RD7pointB=1;
        // C ve D soruları 0 puan

        for (i = 0; i < optionsListRosenbergD7.size(); i++){
            if (optionsListRosenbergD7.get(i).equals("A"))
                countA++;
            else if (optionsListRosenbergD7.get(i).equals("B"))
                countB++;
        }
        return (RD7pointA * countA) + (RD7pointB * countB);
    }


    private float sonucHesaplaRosenbergD1(List<String> optionsListRosenbergD1) {
        float RD1pointA=0;
        float RD1pointB=0;
        float RD1pointC=0;
        float RD1pointD=0;

        for (i = 0; i < optionsListRosenbergD1.size(); i++){
            if (i == 1){
                if (optionsListRosenbergD1.get(i).equals("C")){
                    RD1pointC += 0.17;
                    countC++;
                }
                else if (optionsListRosenbergD1.get(i).equals("D")){
                    RD1pointD += 0.34;
                    countD++;
                }
            }
            if (i == 2){
                if (optionsListRosenbergD1.get(i).equals("C")){
                    RD1pointC += 0.16;
                    countC++;
                }
                else if (optionsListRosenbergD1.get(i).equals("D")){
                    RD1pointD += 0.33;
                    countD++;
                }
            }
            if (i == 3){
                if (optionsListRosenbergD1.get(i).equals("A")){
                    RD1pointA += 0.17;
                    countA++;
                }
                else if (optionsListRosenbergD1.get(i).equals("B")){
                    RD1pointB += 0.33;
                    countB++;
                }
            }
            if (i == 4){
                if (optionsListRosenbergD1.get(i).equals("C")){
                    RD1pointC += 0.25;
                    countC++;
                }
                else if (optionsListRosenbergD1.get(i).equals("D")){
                    RD1pointD += 0.50;
                    countD++;
                }
            }
            if (i == 5){
                if (optionsListRosenbergD1.get(i).equals("C")){
                    RD1pointC += 0.25;
                    countC++;
                }
                else if (optionsListRosenbergD1.get(i).equals("D")){
                    RD1pointD += 0.50;
                    countD++;
                }
            }
            if (i == 6){
                if (optionsListRosenbergD1.get(i).equals("C")){
                    RD1pointC += 0.50;
                    countC++;
                }
                else if (optionsListRosenbergD1.get(i).equals("D")){
                    RD1pointD += 1;
                    countD++;
                }
            }
            if (i == 7){
                if (optionsListRosenbergD1.get(i).equals("C")){
                    RD1pointC += 0.50;
                    countC++;
                }
                else if (optionsListRosenbergD1.get(i).equals("D")){
                    RD1pointD += 1;
                    countD++;
                }
            }
            if (i == 8){
                if (optionsListRosenbergD1.get(i).equals("A")){
                    RD1pointA += 1;
                    countA++;
                }
                else if (optionsListRosenbergD1.get(i).equals("B")){
                    RD1pointB += 0.50;
                    countB++;
                }
            }
            if (i == 9){
                if (optionsListRosenbergD1.get(i).equals("A")){
                    RD1pointA += 0.50;
                    countA++;
                }
                else if (optionsListRosenbergD1.get(i).equals("B")){
                    RD1pointB += 0.25;
                    countB++;
                }
            }
            if (i == 10){
                if (optionsListRosenbergD1.get(i).equals("A")){
                    RD1pointA += 0.50;
                    countA++;
                }
                else if (optionsListRosenbergD1.get(i).equals("B")){
                    RD1pointB += 0.25;
                    countB++;
                }
            }
        }

        return (RD1pointA + RD1pointB + RD1pointC + RD1pointD);
    }

    public float sonucHesaplaDepression(List<String> optionsList){
        for (String option : optionsList){
            if (option.equals("A"))
                countA++;
            if (option.equals("B"))
                countB++;
            if (option.equals("C"))
                countC++;
            if (option.equals("D"))
                countD++;
        }
        return ((pointA*countA) +(pointB*countB)+(pointC*countC)+(pointD*countD));
    }

    private void printRosenbergD7(float pointRosenbergD7, String test) {
        if (pointRosenbergD7 >= 0 && pointRosenbergD7 <= 2){
            binding.healthText.setText("Az Derecede " + test + " Sahipsiniz.");
        }
        if (pointRosenbergD7 > 2 && pointRosenbergD7 <= 4){
            binding.healthText.setText("Orta Derecede " + test + " Sahipsiniz.");
        }
        if (pointRosenbergD7 > 5){
            binding.healthText.setText("Yüksek Derecede " + test + " Sahipsiniz.");
        }
    }


    private void printRosenbergD1(float point, String test) {
        if (point > 0 && point <= 2) {
            binding.healthText.setText("Yüksek Derecede " + test + " Sahibisiniz.");
        }
        if (point > 2 && point <= 4) {
            binding.healthText.setText("Orta Derecede " + test + " Sahibisiniz.");
        }
        if (point > 5 && point <= 6) {
            binding.healthText.setText("Düşük Derecede " + test + " Sahibisiniz.");
        }

    }

    public void printHopeless(float point, String test){
        if (point >= 0 && point <= 3){
            binding.healthText.setText("Minimal Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Şu an içinde bulunduğunuz durum, küçük adımlarla aşılabilecek bir zorluktur. Çözüme odaklanın, çünkü her sorunun bir çözümü vardır.");
            binding.specialText.setText("En küçük ışık, en karanlık odada bile fark edilir.");
        }
        else if (point >= 4 && point <= 8){
            binding.healthText.setText("Hafif Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Belki değişim başlangıcıdır ve şu anki durumunuz, önemli adımlar atarak üzerinden gelebileceğiniz bir evrededir. Küçük değişiklikler büyük farklar yaratır.");
            binding.specialText.setText("Bir fidanın büyümesi zaman alır, umut da öyle. Küçük adımlar, büyük değişimleri getirir.");
        }
        else if (point >= 9 && point <= 14){
            binding.healthText.setText("Orta Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Zorluklar artıyor gibi hissedebilirsiniz, ancak ortada büyük bir potansiyel var. Adım adım ilerleyin, çünkü orta derecedeki sorunlar, kararlılıkla üstesinden gelinebilir.");
            binding.specialText.setText("Bir gemi, sakin denizlerde yapılmaz. Güçlü ol, fırtınaların üstesinden geleceksin.");

        }
        else if (point >= 15 && point <= 20){
            binding.healthText.setText("Şiddetli Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Bu zorlu süreçte olmak zor olabilir, ancak hatırlayın ki en karanlık anlar, en büyük dönüşümlerin habercisidir. Güçlü durun, çünkü şiddetli umutsuzluk, içsel gücünüzü keşfetmenin bir yoludur.");
            binding.specialText.setText("Bir karanlık tünelin sonunda her zaman bir ışık vardır. Şimdi, kendi ışığını yaratma zamanı.");
        }
    }
    public void printDepression(float point, String test){
        if (point >= 0 && point <= 9){
            binding.healthText.setText("Minimal Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Şu an hissettiğiniz duygular, kolayca yönetilebilecek bir düzeyde. İhtiyaç duyduğunuz destekle, bu geçici zorluğun üstesinden gelebilirsiniz.");
            binding.specialText.setText("Bazen en küçük umut ışığı, içindeki karanlıkla savaşmak için yeterli olabilir.");
        }
        else if (point >= 10 && point <= 16){
            binding.healthText.setText("Hafif Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Duygusal iniş çıkışlar, hayatın doğal bir parçasıdır. Şu anda hissettiğiniz, zamanla daha iyiye gitme potansiyeline sahiptir. Kendinize zaman tanıyın ve destek alın.");
            binding.specialText.setText("Depresyon, bir fidanın büyümesi gibi zaman alır. Küçük adımlar, büyük bir içsel dönüşüm getirebilir.");
        }
        else if (point >= 17 && point <= 29){
            binding.healthText.setText("Orta Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Depresyonun orta derecesinde olmak, zorlu bir mücadele olabilir. Ancak, bu duygularla başa çıkmanın yollarını araştırmak ve profesyonel yardım almak, iyileşme sürecini hızlandırabilir.");
            binding.specialText.setText("Fırtınalar, bir geminin gerçek gücünü ortaya çıkarır. Bu zorlu zaman, içsel gücünü keşfetme fırsatıdır.");
        }
        else if (point >= 30 && point <= 63){
            binding.healthText.setText("Şiddetli Derecede "+ test + " Sahibisiniz.");
            binding.suggestionText.setText("Bu zorlu durumda olmak gerçekten zorlayıcıdır, ancak profesyonel yardım ve sevdiklerinizin desteği ile iyileşme şansınız vardır. Güçlü olun, çünkü şiddetli depresyon, içsel dayanıklılığınızı keşfetmenin bir yoludur.");
            binding.specialText.setText("Karanlık tünelin sonunda bir ışık her zaman vardır, ve bu ışığı görmek için kendi içsel ışığını yaratma zamanı geldi.");
        }
    }

    public void backToTheFirstPage(View view) {
        Intent intent = new Intent(LastPageActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void seeAnswers(View view) {
        Intent intent = new Intent(LastPageActivity.this, AnswersActivity.class);
        if (whichTest.equals("depression"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListDepression));
        if (whichTest.equals("hopeless"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListHopeless));
        if (whichTest.equals("rosenbergD1"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListRosenbergD1));
        if (whichTest.equals("rosenbergD7"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListRosenbergD7));
        startActivity(intent);


    }

    public void seeAllEvaluate(View view) {
        //Toast.makeText(this, "Değerlendirme sayfasına gidilecek", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LastPageActivity.this, EvaluatesActivity.class);
        startActivity(intent);
    }

    public void makeEvaluate(View view) {
        Intent intent = new Intent(LastPageActivity.this, MakeEvaluateActivity.class);
        intent.putExtra("whichTest", whichTest); // MakeEvaluateActivity'den geri döndüğümüzde hata çıkmasın diye.
        startActivity(intent);
    }

    @Override    //optionsMenu oluşturulduğunda ne olucak, burada menu layout'unu koda bağlayacağız
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.last_page_menu, menu); // menu aktiviteye bağlandı
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.seeAnswers){
            Intent intent = new Intent(LastPageActivity.this, AnswersActivity.class);
            if (whichTest.equals("depression"))
                intent.putStringArrayListExtra("answers", new ArrayList<>(answersListDepression));
            if (whichTest.equals("hopeless"))
                intent.putStringArrayListExtra("answers", new ArrayList<>(answersListHopeless));
            startActivity(intent);
        }

        if (item.getItemId() == R.id.seeEvaulate){
            Toast.makeText(this, "Değerlendirme sayfasına gidilecek", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(LastPageActivity.this, EvaluatesActivity.class);
            //startActivity(intent);
        }

        if (item.getItemId() == R.id.goBackMain){
            Intent intent = new Intent(LastPageActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}