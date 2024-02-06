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

import com.alperenmengi.duyguanaliziprototip.Camera.facialExpressionRecognition;
import com.alperenmengi.duyguanaliziprototip.Models.QuestionModel;
import com.alperenmengi.duyguanaliziprototip.R;
import com.alperenmengi.duyguanaliziprototip.Views.LastPageActivity;
import com.alperenmengi.duyguanaliziprototip.Views.MainActivity;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityHopelessBinding;
import com.alperenmengi.duyguanaliziprototip.databinding.ActivityRosenbergD1Binding;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RosenbergD1Activity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{


    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;
    private facialExpressionRecognition facialExpressionRecognition;

    private ActivityRosenbergD1Binding binding;
    private List<QuestionModel> questionModelList;
    private QuestionModel currentQuestion1;

    int currentQuestion = 0;
    boolean isClickButton = false;
    String valueChoose = "";
    private Button lastClickedButton = null; // Son tıklanan butonun referansını saklar
    private ArrayList<String> choosenAnswersList;
    private ArrayList<String> choosenOptionList;
    String rosenbergD1 = "rosenbergD1";

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding = ActivityRosenbergD1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        choosenAnswersList = new ArrayList<>();
        choosenOptionList = new ArrayList<>();
        questionModelList = new ArrayList<>();
        addQuestions();
        soruCevaplar();

        System.loadLibrary("opencv_java3");

        int MY_PERMISSIONS_REQUEST_CAMERA=0;
        // if camera permission is not given it will ask for it on device
        if (ContextCompat.checkSelfPermission(RosenbergD1Activity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(RosenbergD1Activity.this, new String[]
                    {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        mOpenCvCameraView=findViewById(R.id.frame_Surface);
        mOpenCvCameraView.setAlpha(0); // kamera görüntüsünü kapatmak için
        mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
        mOpenCvCameraView.setCvCameraViewListener(this);

        try{
            // input size of model is 48
            int inputSize=48;
            facialExpressionRecognition = new facialExpressionRecognition(getAssets(),RosenbergD1Activity.this,
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
                intent.putExtra("test", rosenbergD1);
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
                RosenbergD1Activity.this.finish();
            }
            else{//son soru değilse diğer soruları getir
                soruCevaplar(); // diğer soru ve cevapları yüklemek için
                isClickButton = false; // seçilen cevap olmadığını belirtmek için
                valueChoose = "";
            }
        }
        else
            Toast.makeText(this, "Lütfen bir cevap seçiniz!", Toast.LENGTH_LONG).show();
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
        questionModelList.add(new QuestionModel("Kendimi en az diğer insanlar kadar değerli buluyorum.", "Çok Doğru", "Doğru ", "Yanlış ", "Çok Yanlış"));
        questionModelList.add(new QuestionModel("Bazı olumlu özelliklerim olduğunu düşünüyorum.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Genelde kendimi başarısız bir kişi olarak görme eğilimindeyim.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Ben de diğer insanların birçoğunun yapabildiği kadar birşeyler yapabilirim.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Kendimde gurur duyacak fazla birşey bulamıyorum.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Kendime karşı olumlu bir tutum içindeyim.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Genel olarak kendimden memnunum.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Kendime karşı daha fazla saygı duyabilmeyi isterdim.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Bazen kesinlikle kendimin bir işe yaramadığını düşünüyorum.", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));
        questionModelList.add(new QuestionModel("Bazen kendimin hiç de yeterli bir insan olmadığımı düşünüyorum", "ÇOK DOĞRU", "DOĞRU ", "YANLIŞ ", "ÇOK YANLIŞ"));

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
            yapayZekaSonuc = (int) (( (double) countMutlu / countToplamDuygu) * 100) / 50;
            if(yapayZekaSonuc > 5)
                yapayZekaSonuc = 5;
            System.out.println("if içindeki yapayZekaSonuc : " + yapayZekaSonuc);
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
            yapayZekaSonuc = (int) (((double) max / countToplamDuygu) * 100) / 50;
            System.out.println("rosenberg");
            /*if(yapayZekaSonuc < 3)
                yapayZekaSonuc = 3;*/
            System.out.println("fonksiyonun elsesi yapayZekaSonuc : " + yapayZekaSonuc);
            kontrol = 0;
            kontrolDizisi[0] = yapayZekaSonuc;
            kontrolDizisi[1] = kontrol;
        }
        return kontrolDizisi;
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

        //kameradan alınan görüntüyü analiz edecek olan sınıfa gönderiyoruz. Burada kişinin duygusu tespit ediliyor.
        mRgba = facialExpressionRecognition.recognizeImage(mRgba);
        Log.d("Bilgilendirme", "mRgba x ve y : " + mRgba.cols() + "x" + mRgba.rows());


        return mRgba;
    }

}