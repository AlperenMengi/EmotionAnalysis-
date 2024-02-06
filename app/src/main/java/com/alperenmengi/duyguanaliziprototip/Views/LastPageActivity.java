package com.alperenmengi.duyguanaliziprototip.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Camera.facialExpressionRecognition;
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
    private ArrayList<String> answersListAnxiety;
    private ArrayList<String> answersListWellBeing;
    private ArrayList<String> optionsListDepression;
    private ArrayList<String> optionsListHopeless;
    private ArrayList<String> optionsListRosenbergD1;
    private ArrayList<String> optionsListRosenbergD7;
    private ArrayList<String> optionsListAnxiety;
    private ArrayList<String> optionsListWellBeing;
    private int yapayZekaSonuc;

    float countA = 0;
    float countB = 0;
    float countC = 0;
    float countD = 0;
    float countE = 0;
    float pointA = 0;
    float pointB = 1;
    float pointC = 2;
    float pointD = 3;
    float pointE = 4;
    int pointHopeless = 0;
    int i = 0;
    public static float testSonucu;
    public static float genelSonuc;
    public static int yzSonucu;
    String whichTest;



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

                pointHopeless = (int) sonucHesaplaHopeless(optionsListHopeless);
                testSonucu = pointHopeless;
                yapayZekaSonuc = getIntent().getIntExtra("azalt", 0);
                yzSonucu = yapayZekaSonuc;
                pointHopeless -= yapayZekaSonuc;
                if (pointHopeless < 0)
                    pointHopeless = 0;
                if (yapayZekaSonuc == 0){
                    yapayZekaSonuc = getIntent().getIntExtra("arttır", 0);
                    yzSonucu = yapayZekaSonuc;
                    pointHopeless += yapayZekaSonuc;
                    if (pointHopeless > 20)
                        pointHopeless = 20;
                }
                genelSonuc = pointHopeless;
                binding.resultText.setText("Sonucunuz : " + pointHopeless + "/20 puan");
                printHopeless(pointHopeless, "Umutsuzluk");
            }

            if (whichTest.equals("depression")){
                answersListDepression = getIntent().getStringArrayListExtra("answers");
                optionsListDepression = getIntent().getStringArrayListExtra("options");

                int pointDepression = (int) sonucHesaplaDepression(optionsListDepression);
                testSonucu = pointDepression;
                yapayZekaSonuc = getIntent().getIntExtra("azalt", 0);
                yzSonucu = yapayZekaSonuc;
                pointDepression -= yapayZekaSonuc;
                if (pointDepression < 0)
                    pointDepression = 0;
                if (yapayZekaSonuc == 0){
                    yapayZekaSonuc = getIntent().getIntExtra("arttır", 0);
                    yzSonucu = yapayZekaSonuc;
                    pointDepression += yapayZekaSonuc;
                    if (pointDepression > 63)
                        pointDepression = 63;
                }
                genelSonuc = pointDepression;
                binding.resultText.setText("Sonucunuz : " + pointDepression + "/63 puan");
                printDepression(pointDepression, "Depresyon");

            }
            if (whichTest.equals("rosenbergD1")){
                answersListRosenbergD1 = getIntent().getStringArrayListExtra("answers");
                optionsListRosenbergD1  = getIntent().getStringArrayListExtra("options");

                float pointRosenbergD1 = sonucHesaplaRosenbergD1(optionsListRosenbergD1);
                testSonucu = pointRosenbergD1;
                yapayZekaSonuc = getIntent().getIntExtra("azalt", 0);
                yzSonucu = yapayZekaSonuc;
                pointRosenbergD1 -= yapayZekaSonuc;
                if (pointRosenbergD1 < 0)
                    pointRosenbergD1 = 0;
                if (yapayZekaSonuc == 0){
                    yapayZekaSonuc = getIntent().getIntExtra("arttır", 0);
                    yzSonucu = yapayZekaSonuc;
                    pointRosenbergD1 += yapayZekaSonuc;
                    if (pointRosenbergD1 > 6)
                        pointRosenbergD1 = 6;
                }
                genelSonuc = pointRosenbergD1;
                binding.resultText.setText("Sonucunuz : " + String.format("%.2f", pointRosenbergD1) + "/6 puan");
                printRosenbergD1(pointRosenbergD1, "Benlik Saygısı'na");
            }
            if (whichTest.equals("rosenbergD7")){
                answersListRosenbergD7 = getIntent().getStringArrayListExtra("answers");
                optionsListRosenbergD7 =getIntent().getStringArrayListExtra("options");

                float pointRosenbergD7 = sonucHesaplaRosenbergD7(optionsListRosenbergD7);
                testSonucu = pointRosenbergD7;
                yapayZekaSonuc = getIntent().getIntExtra("azalt", 0);
                yzSonucu = yapayZekaSonuc;
                pointRosenbergD7 -= yapayZekaSonuc;
                if (pointRosenbergD7 < 0)
                    pointRosenbergD7 = 0;
                if (yapayZekaSonuc == 0){
                    yapayZekaSonuc = getIntent().getIntExtra("arttır", 0);
                    yzSonucu = yapayZekaSonuc;
                    pointRosenbergD7 += yapayZekaSonuc;
                    if (pointRosenbergD7 > 10)
                        pointRosenbergD7 = 10;
                }
                genelSonuc = pointRosenbergD7;
                binding.resultText.setText("Sonucunuz : " + String.format("%.2f", pointRosenbergD7)  + "/10 puan");
                printRosenbergD7(pointRosenbergD7, "Psikosomatik Belirti'ye");
            }
            if (whichTest.equals("anxiety")){
                answersListAnxiety = getIntent().getStringArrayListExtra("answers");
                optionsListAnxiety =getIntent().getStringArrayListExtra("options");

                int pointAnxiety = sonucHesaplaAnxiety(optionsListAnxiety);
                testSonucu = pointAnxiety;
                yapayZekaSonuc = getIntent().getIntExtra("azalt", 0);
                yzSonucu = yapayZekaSonuc;
                pointAnxiety -= yapayZekaSonuc;
                if (pointAnxiety < 0)
                    pointAnxiety = 0;
                if (yapayZekaSonuc == 0){
                    yapayZekaSonuc = getIntent().getIntExtra("arttır", 0);
                    yzSonucu = yapayZekaSonuc;
                    pointAnxiety += yapayZekaSonuc;
                    if (pointAnxiety > 63)
                        pointAnxiety = 63;
                }
                genelSonuc = pointAnxiety;
                binding.resultText.setText("Sonucunuz : " + pointAnxiety + "/63 puan");
                printAnxiety(pointAnxiety, "Anksiyete'ye");
            }
            if (whichTest.equals("wellbeing")){
                answersListWellBeing = getIntent().getStringArrayListExtra("answers");
                optionsListWellBeing =getIntent().getStringArrayListExtra("options");

                int pointWellBeing= sonucHesaplaWellBeing(optionsListWellBeing);
                testSonucu = pointWellBeing;
                yapayZekaSonuc = getIntent().getIntExtra("arttır", 0);
                yzSonucu = yapayZekaSonuc;
                pointWellBeing -= yapayZekaSonuc;
                if (pointWellBeing < 14)
                    pointWellBeing = 14;
                if (yapayZekaSonuc == 0){
                    yapayZekaSonuc = getIntent().getIntExtra("azalt", 0);
                    yzSonucu = yapayZekaSonuc;
                    pointWellBeing += yapayZekaSonuc;
                    if (pointWellBeing > 70)
                        pointWellBeing = 70;
                }
                genelSonuc = pointWellBeing;
                binding.resultText.setText("Sonucunuz : " + pointWellBeing + "/70 puan");
                printWellBeing(pointWellBeing, "İyi Oluş Seviyesi'ne");
            }
        }
    }

    private int sonucHesaplaWellBeing(ArrayList<String> optionsListWellBeing) {
        for (String option : optionsListWellBeing) {
            if (option.equals("A"))
                countA++;
            if (option.equals("B"))
                countB++;
            if (option.equals("C"))
                countC++;
            if (option.equals("D"))
                countD++;
            if (option.equals("E"))
                countE++;
        }
        return (int) (((pointA+1)*countA)+((pointB+1)*countB)+((pointC+1)*countC)+((pointD+1)*countD)+((pointE+1)*countE));
    }

    private int sonucHesaplaAnxiety(ArrayList<String> optionsListAnxiety) {
        for (String option : optionsListAnxiety) {
            if (option.equals("A"))
                countA++;
            if (option.equals("B"))
                countB++;
            if (option.equals("C"))
                countC++;
            if (option.equals("D"))
                countD++;
        }
        return (int) ((pointA*countA) +(pointB*countB)+(pointC*countC)+(pointD*countD));
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

    private void printWellBeing(int pointWellBeing, String test) {
        if (pointWellBeing >= 0 && pointWellBeing <=32){
            binding.healthText.setText("Düşük " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.dusukiyiolus);
            binding.specialText.setText("Gökyüzündeki bulutlar, güneşi görmek için bir fırsattır.");

        }
        if (pointWellBeing >= 33 && pointWellBeing <=40){
            binding.healthText.setText("Orta " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.ortaiyiolus);
            binding.specialText.setText("Hayatta inişler ve çıkışlar normaldir, önemli olan yola devam etmektir.");

        }
        if (pointWellBeing >= 41 && pointWellBeing <=48){
            binding.healthText.setText("İyi " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.iyiiyiolus);
            binding.specialText.setText("Güzel anları yaşayın, çünkü yaşamın değeri bu anlardan gelir.");

        }
        if (pointWellBeing >= 49 && pointWellBeing <=56){
            binding.healthText.setText("Yüksek " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.azpsikoloji);
            binding.specialText.setText("Kendinizi sevin, çünkü kendi içindeki güzellik, dışarıya ışık saçar.");

        }
        if (pointWellBeing >= 57 && pointWellBeing <=70){
            binding.healthText.setText("Çok Yüksek " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.yuksekiyiolus);
            binding.specialText.setText("Hayatta gerçek mutluluk, içsel bir huzur ve uyumla gelir.");

        }
    }

    private void printAnxiety(int pointAnxiety, String test) {
        if (pointAnxiety >= 0 && pointAnxiety <= 15){
            binding.healthText.setText("Hafif Derecede " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.hafifanksiyete);
            binding.specialText.setText("Gökyüzündeki bulutlar, güneşin ardında hep parlıyor.");

        }
        if (pointAnxiety >= 16 && pointAnxiety <= 25){
            binding.healthText.setText("Orta Derecede " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.ortaanksiyete);
            binding.specialText.setText("Fırtınada kaybolmadan önce, rüzgarın gücünü hatırla.");

        }
        if (pointAnxiety >= 26 && pointAnxiety <= 63){
            binding.healthText.setText("Şiddetli Derecede " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.siddetlianksiyete);
            binding.specialText.setText("Her fırtına, bir gün yerini sakin bir denize bırakır.");

        }
    }

    private void printRosenbergD7(float pointRosenbergD7, String test) {
        if (pointRosenbergD7 >= 0 && pointRosenbergD7 <= 2){
            binding.healthText.setText("Az Derecede " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.azpsikoloji);
            binding.specialText.setText("Azaltılmış stres, sağlıklı bir hayatın anahtarıdır.");

        }
        if (pointRosenbergD7 > 2 && pointRosenbergD7 <= 4){
            binding.healthText.setText("Orta Derecede " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.ortapsikoloji);
            binding.specialText.setText("Bedeniniz size mesaj gönderiyorsa, ruhunuzu dinlemeyi unutmayın.");

        }
        if (pointRosenbergD7 >= 5){
            binding.healthText.setText("Yüksek Derecede " + test + " Sahipsiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.yuksekpsikoloji);
            binding.specialText.setText("Zihin ve beden arasındaki dengeyi bulmak, sağlıklı bir yaşamın temelidir.");

        }
    }

    private void printRosenbergD1(float point, String test) {
        if (point >= 0 && point <= 2) {
            binding.healthText.setText("Yüksek Derecede " + test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.yuksekbenliksaygisi);
            binding.specialText.setText("Kendi güzelliklerinizi keşfedin, çünkü kendi içinizde bir hazine saklıdır.");

        }
        if (point > 2 && point <= 4) {
            binding.healthText.setText("Orta Derecede " + test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.ortabenliksaygisi);
            binding.specialText.setText("Kendi değerinizi unutmuşsanız, hatırlamak asıl adımdır.");

        }
        if (point > 4 && point <= 6) {
            binding.healthText.setText("Düşük Derecede " + test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.dusukbenliksaygisi);
            binding.specialText.setText("İçsel ışığınızı bulun, çünkü karanlık sadece geçici bir durumdur.");

        }

    }

    public void printHopeless(float point, String test){
        if (point >= 0 && point <= 3){
            binding.healthText.setText("Minimal Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.minimalumutsuzluk);
            binding.specialText.setText("En küçük ışık, en karanlık odada bile fark edilir.");
        }
        else if (point >= 4 && point <= 8){
            binding.healthText.setText("Hafif Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.hafifumutsuzluk);
            binding.specialText.setText("Bir fidanın büyümesi zaman alır, umut dua öyle. Küçük adımlar, büyük değişimleri getirir.");
        }
        else if (point >= 9 && point <= 14){
            binding.healthText.setText("Orta Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.ortaumutsuzluk);
            binding.specialText.setText("Bir gemi, sakin denizlerde yapılmaz. Güçlü ol, fırtınaların üstesinden geleceksin.");

        }
        else if (point >= 15 && point <= 20){
            binding.healthText.setText("Şiddetli Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.siddetliumutsuzluk);
            binding.specialText.setText("Bir karanlık tünelin sonunda her zaman bir ışık vardır. Şimdi, kendi ışığını yaratma zamanı.");
        }
    }

    public void printDepression(float point, String test){
        if (point >= 0 && point <= 9){
            binding.healthText.setText("Minimal Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.minimaldepresyon);
            binding.specialText.setText("Bazen en küçük umut ışığı, içindeki karanlıkla savaşmak için yeterli olabilir.");
        }
        else if (point >= 10 && point <= 16){
            binding.healthText.setText("Hafif Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.hafifdepresyon);
            binding.specialText.setText("Depresyon, bir fidanın büyümesi gibi zaman alır. Küçük adımlar, büyük bir içsel dönüşüm getirebilir.");
        }
        else if (point >= 17 && point <= 29){
            binding.healthText.setText("Orta Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.ortadepreson);
            binding.specialText.setText("Fırtınalar, bir geminin gerçek gücünü ortaya çıkarır. Bu zorlu zaman, içsel gücünü keşfetme fırsatıdır.");
        }
        else if (point >= 30 && point <= 63){
            binding.healthText.setText("Şiddetli Derecede "+ test + " Sahibisiniz.");
            binding.emotionPhotoView.setImageResource(R.drawable.siddetlidepresyon);
            binding.specialText.setText("Karanlık tünelin sonusnda bir ışık her zaman vardır, ve bu ışığı görmek için kendi içsel ışığını yaratma zamanı geldi.");
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
        if (whichTest.equals("anxiety"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListAnxiety));
        if (whichTest.equals("wellbeing"))
            intent.putStringArrayListExtra("answers", new ArrayList<>(answersListWellBeing));
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
    public void goToDetailResult(View view) {
        Intent intent = new Intent(LastPageActivity.this, DetailResultActivity.class);
        startActivity(intent);
    }

/*
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
    }*/


}