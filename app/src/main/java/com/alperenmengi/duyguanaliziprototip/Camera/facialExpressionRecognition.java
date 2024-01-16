package com.alperenmengi.duyguanaliziprototip.Camera;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.alperenmengi.duyguanaliziprototip.R;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class facialExpressionRecognition {
    private Interpreter interpreter;
    private int INPUT_SIZE;
    private int height=0;
    private int width=0;
    private GpuDelegate gpuDelegate=null;
    private CascadeClassifier cascadeClassifier;

    //diğer aktivilerde kullanılmak için duygu sayılarını tuttum.
    public static int countSaskin = 0;
    public static int countNotr = 0;
    public static int countKorkmus = 0;
    public static int countSinirli = 0;
    public static int countIgrenmıs = 0;
    public static int countUzgun = 0;
    public static int countMutlu = 0;

    public facialExpressionRecognition(AssetManager assetManager, Context context, String modelPath, int inputSize) throws IOException {
        INPUT_SIZE=inputSize;
        Interpreter.Options options=new Interpreter.Options();
        gpuDelegate=new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(4);//telefonun performasına göre thread sayısı düşürülebilir.
        interpreter=new Interpreter(loadModelFile(assetManager,modelPath),options);
        Log.d("facial_Expression","Model is loaded");

        try {
            InputStream is=context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            File cascadeDir=context.getDir("cascade",Context.MODE_PRIVATE);
            File mCascadeFile=new File(cascadeDir,"haarcascade_frontalface_alt");
            FileOutputStream os=new FileOutputStream(mCascadeFile);
            byte[] buffer=new byte[4096];
            int byteRead;
            while ((byteRead=is.read(buffer)) !=-1){
                os.write(buffer,0,byteRead);
            }
            is.close();
            os.close();
            cascadeClassifier=new CascadeClassifier(mCascadeFile.getAbsolutePath());
            Log.d("facial_Expression","Classifier is loaded");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //BURADAKİ mat veya bitmap boyutları, kişinin yüzünün etrafına çizilen karenin boyutlarıdır
    public Mat recognizeImage(Mat mat_image){
        Core.flip(mat_image.t(),mat_image,1);

        //burada fotoğraki griye boyama işlemi yapılıyor
        Mat grayscaleImage=new Mat();
        Imgproc.cvtColor(mat_image,grayscaleImage,Imgproc.COLOR_RGBA2GRAY);
        height=grayscaleImage.height();
        width=grayscaleImage.width();

        int absoluteFaceSize=(int)(height * 0.1);
        MatOfRect faces=new MatOfRect();
        if(cascadeClassifier !=null){
            //                                  input         output
            cascadeClassifier.detectMultiScale(grayscaleImage,faces,1.1,2,2,
                    new Size(absoluteFaceSize,absoluteFaceSize),new Size());
        }
        Rect[] faceArray=faces.toArray();
        for (int i=0;i<faceArray.length;i++){
            Imgproc.rectangle(mat_image,faceArray[i].tl(),faceArray[i].br(),new Scalar(0,255,0,255),2);
            Rect roi=new Rect((int)faceArray[i].tl().x,(int)faceArray[i].tl().y,
                    ((int)faceArray[i].br().x)-(int)(faceArray[i].tl().x),
                    ((int)faceArray[i].br().y)-(int)(faceArray[i].tl().y));
            Mat cropped_rgba=new Mat(mat_image,roi);//
            Bitmap bitmap=null;
            bitmap=Bitmap.createBitmap(cropped_rgba.cols(),cropped_rgba.rows(),Bitmap.Config.ARGB_8888);

            Utils.matToBitmap(cropped_rgba,bitmap);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,48,48,false);
            ByteBuffer byteBuffer=convertBitmapToByteBuffer(scaledBitmap);
            System.out.println("byteBuffer :" + byteBuffer);
            float[][] emotion=new float[1][1];
            interpreter.run(byteBuffer,emotion);

            float emotion_v=(float)Array.get(Array.get(emotion,0),0);
            Log.d("facial_expression","Output:  "+ emotion_v);
            String emotion_s=get_emotion_text(emotion_v);

            Log.d("emotion","Output:  "+ emotion_s);


            Imgproc.putText(mat_image,emotion_s+" ("+emotion_v+")",
                    new Point((int)faceArray[i].tl().x+10,(int)faceArray[i].tl().y+20),
                    1,1.5,new Scalar(255,255,255,150),2);
        }
        Core.flip(mat_image.t(),mat_image,0);
        return mat_image;
    }
    //emotion değerine göre duyguları belirleme
    private String get_emotion_text(float emotion_v) {
        String val="";

        if(emotion_v>=0 & emotion_v<0.5){
            val="Saskin";
            countSaskin++;
        }
        else if(emotion_v>=0.5 & emotion_v <1.5){
            val="Korkmus";
            countKorkmus++;
        }
        else if(emotion_v>=1.5 & emotion_v <2.5){
            val="Sinirli";
            countSinirli++;
        }
        else if(emotion_v>=2.5 & emotion_v <3.5){
            val="Notr";
            countNotr++;
        }
        else if(emotion_v>=3.5 & emotion_v <4.5){
            val="Uzgun";
            countUzgun++;
        }
        else if(emotion_v>=4.5 & emotion_v <5.5){
            val="Igrenmis";
            countIgrenmıs++;
        }
        else {
            val="Mutlu";
            countMutlu++;
        }
        return val;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap scaledBitmap) {
        ByteBuffer byteBuffer;
        int size_image=INPUT_SIZE;//48

        byteBuffer=ByteBuffer.allocateDirect(4 * size_image * size_image * 3);
        // 4 is multiplied for float input
        // 3 is multiplied for rgb
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues=new int[size_image*size_image];
        scaledBitmap.getPixels(intValues,0,scaledBitmap.getWidth(),0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight());
        int pixel=0;
        for(int i =0;i<size_image;++i){
            for(int j=0;j<size_image;++j){
                final int val=intValues[pixel++];
                // now put float value to bytebuffer
                // scale image to convert image from 0-255 to 0-1
                byteBuffer.putFloat((((val>>16)&0xFF))/255.0f);
                byteBuffer.putFloat((((val>>8)&0xFF))/255.0f);
                byteBuffer.putFloat(((val & 0xFF))/255.0f);

            }
        }
        return byteBuffer;
        // check one more time it is important else you will get error
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        // this will give description of file
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(modelPath);
        // create a inputsteam to read file
        FileInputStream inputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = assetFileDescriptor.getStartOffset();
        long declaredLength = assetFileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);

    }

}