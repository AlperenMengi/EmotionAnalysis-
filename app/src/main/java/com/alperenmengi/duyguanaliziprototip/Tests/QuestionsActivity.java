package com.alperenmengi.duyguanaliziprototip.Tests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.alperenmengi.duyguanaliziprototip.Models.QuestionModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Views.LastPageActivity;
import com.alperenmengi.duyguanaliziprototip.Views.MainActivity;
import com.alperenmengi.duyguanaliziprototip.Camera.facialExpressionRecognition;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityQuestionsBinding;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    //Implementation for Camera
    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;
    // call java class
    private facialExpressionRecognition facialExpressionRecognition;

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

    int countMutlu;
    int countSinirli;
    int countSaskin;
    int countUzgun;
    int countNotr;
    int countIgrenmis;
    int countKorkmus;
    int countToplamDuygu;
    int yapayZekaSonuc;

    // For Camera
    private BaseLoaderCallback mLoaderCallback =new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i("Bilgilendirme","OpenCv Is loaded");
                    mOpenCvCameraView.enableView();
                }
                default:
                {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ekranın kapanmamasını sağlıyor
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        choosenAnswersList = new ArrayList<>();
        choosenOptionList = new ArrayList<>();
        questionModelList = new ArrayList<>();
        addQuestions();
        soruCevaplar();

        System.loadLibrary("opencv_java3");

        //closeCamerabutton = findViewById(R.id.closeCameraButton);

        int MY_PERMISSIONS_REQUEST_CAMERA=0;
        // if camera permission is not given it will ask for it on device
        if (ContextCompat.checkSelfPermission(QuestionsActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(QuestionsActivity.this, new String[]
                    {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        mOpenCvCameraView=findViewById(R.id.frame_Surface);
        mOpenCvCameraView.setAlpha(0); // kamera görüntüsünü kapatmak için
        mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
        mOpenCvCameraView.setCvCameraViewListener(this);

        try{
            // input size of model is 48
            int inputSize=48;
            facialExpressionRecognition = new facialExpressionRecognition(getAssets(),QuestionsActivity.this,
                    "model300.tflite",inputSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
                int[] dizi;

                Intent intent = new Intent(this, LastPageActivity.class);
                intent.putExtra("test", depression);
                intent.putStringArrayListExtra("answers", choosenAnswersList);
                intent.putStringArrayListExtra("options", choosenOptionList);

                dizi = yapayZekaSonucuHesapla();
                if (dizi[1] == 1)
                    intent.putExtra("azalt", dizi[0]);
                else if (dizi[1] == 0)
                    intent.putExtra("arttır", dizi[0]);

                System.out.println("YAPAY ZEKADAN GELEN DEĞER : " + dizi[0]);

                Log.d("Bilgilendirme", "Mutlu Sayısı : " + facialExpressionRecognition.countMutlu);
                Log.d("Bilgilendirme", "İgrenmis Sayısı : " + facialExpressionRecognition.countIgrenmıs);
                Log.d("Bilgilendirme", "Uzgun Sayısı : " + facialExpressionRecognition.countUzgun);
                Log.d("Bilgilendirme", "Notr Sayısı : " + facialExpressionRecognition.countNotr);
                Log.d("Bilgilendirme", "Sinirli Sayısı : " + facialExpressionRecognition.countSinirli);
                Log.d("Bilgilendirme", "Saskin Sayısı : " + facialExpressionRecognition.countSaskin);
                Log.d("Bilgilendirme", "Korkmus Sayısı : " + facialExpressionRecognition.countKorkmus);

                startActivity(intent);
                QuestionsActivity.this.finish();
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

    public int[] yapayZekaSonucuHesapla(){
        int kontrol;
        int[] kontrolDizisi = new int[2];
        countMutlu = facialExpressionRecognition.countMutlu;
        countKorkmus= facialExpressionRecognition.countKorkmus;
        countNotr = facialExpressionRecognition.countNotr;
        countSaskin = facialExpressionRecognition.countSaskin;
        countSinirli = facialExpressionRecognition.countSinirli;
        countUzgun = facialExpressionRecognition.countUzgun;
        countIgrenmis = facialExpressionRecognition.countIgrenmıs;
        countToplamDuygu = countMutlu+countKorkmus+countNotr+countSaskin+countSinirli+countUzgun+countIgrenmis;

        if ((countMutlu > countKorkmus) && (countMutlu > countNotr) && (countMutlu > countSaskin) && (countMutlu > countSinirli) && (countMutlu > countUzgun) && (countMutlu > countIgrenmis)){
            System.out.println("countMutlu : " + countMutlu);
            System.out.println("countToplamDuygu : " + countToplamDuygu);
            yapayZekaSonuc = (int) (( (double) countMutlu / countToplamDuygu) * 100) / 10;
            if(yapayZekaSonuc > 10)
                yapayZekaSonuc = 10;
            System.out.println("if içindeki yapayZrkaSonuc : " + yapayZekaSonuc);
            kontrol = 1;
            kontrolDizisi[0] = yapayZekaSonuc;
            kontrolDizisi[1] = kontrol;
        }
        else{
            int[] dizi = {countKorkmus,countNotr,countSaskin,countSinirli,countUzgun,countIgrenmis};
            int i;
            int max;
            max = dizi[0];
            for (i = 0; i < dizi.length; i++){
                if (dizi[i] > max){
                    max = dizi[i];
                }
            }
            System.out.println("countMutlu : " + countMutlu);
            System.out.println("countToplamDuygu : " + countToplamDuygu);
            yapayZekaSonuc = (int) (((double) max / countToplamDuygu) * 100) / 10;
            System.out.println("fonksiyonun elsesi yapayZekaSonuc : " + yapayZekaSonuc);

            kontrol = 0;
            kontrolDizisi[0] = yapayZekaSonuc;
            kontrolDizisi[1] = kontrol;

        }
        return kontrolDizisi;
    }


    // bir soru geri gelme butonu
    public void back(View View) {
        if (currentQuestion == 0) {
            Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
            startActivity(intent);
            QuestionsActivity.this.finish();
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
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Cezalandırıldığımı düşünmüyorum.", "Cezalandırılabileceğimi hissediyorum.", "Cezalandırılmayı bekliyorum.", "Cezalandırıldığımı düşünüyorum."));
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
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Sağlığım hakkında fazla endişem yok.", "Ağrı, sancı, mide bozukluğu, veya kabızlık gibi rahıtsızlıklar beni endişelendiriyor.", "Sağlığımın bozulmasından endişeleniyorum ve bu sebeple kafamı başka şeylere vermekte zorlanıyorum.", "Sağlığım hakkında o kadar endişeliyim ki başka hiçbir şey düşünemiyorum."));
        questionModelList.add(new QuestionModel("Aşağıdaki önermelerden size en uygun olanı seçin.", "Sekse karşı ilgimde herhangi bir değişiklik yok.", "Eskisine göre sekse olan ilgim biraz azaldı.", "Sekse olan ilgim büyük ölçüde azaldı.", "Sekse olan ilgimi tamamen kaybettim."));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            //if load success
            Log.d("Bilgilendirme","Opencv initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            //if not loaded
            Log.d("Bilgilendirme","Opencv is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,mLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }
    }

    public void onCameraViewStarted(int width ,int height){
        mRgba = new Mat(height,width, CvType.CV_8UC4);
        mGray = new Mat(height,width,CvType.CV_8UC1);
    }

    public void onCameraViewStopped(){
        mRgba.release();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        Mat rotImage = Imgproc.getRotationMatrix2D(new Point(mRgba.cols() / 2,
                mRgba.rows() / 2), 180, 1.0);
        Imgproc.warpAffine(mRgba, mRgba, rotImage, mRgba.size());
        Imgproc.warpAffine(mGray, mGray, rotImage, mRgba.size());

        // Call the facial expression recognition
        mRgba = facialExpressionRecognition.recognizeImage(mRgba);
        Log.d("Bilgilendirme", "mRgba x ve y : " + mRgba.cols() + "x" + mRgba.rows());
        // Rotate the frame back to the original orientation
        //Core.rotate(mRgba, mRgba, Core.ROTATE_90_COUNTERCLOCKWISE);

        return mRgba;

    }
}