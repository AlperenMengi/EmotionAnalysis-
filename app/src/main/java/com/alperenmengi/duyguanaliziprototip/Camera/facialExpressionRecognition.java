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
    // define interpreter
    // Before this implement tensorflow to build.gradle file
    private Interpreter interpreter;
    // define input size
    private int INPUT_SIZE;
    // define height and width of original frame
    private int height=0;
    private int width=0;
    // now define Gpudelegate
    // it is use to implement gpu in interpreter
    private GpuDelegate gpuDelegate=null;

    // now define cascadeClassifier for face detection
    private CascadeClassifier cascadeClassifier;

    public int countSaskin = 0;
    public int countNotr = 0;
    public int countKorkmus = 0;
    public int countSinirli = 0;
    public int countIgrenmıs = 0;
    public int countUzgun = 0;
    public int countMutlu = 0;

    // now call this in CameraActivity
    public facialExpressionRecognition(AssetManager assetManager, Context context, String modelPath, int inputSize) throws IOException {
        INPUT_SIZE=inputSize;
        // set GPU for the interpreter
        Interpreter.Options options=new Interpreter.Options();
        gpuDelegate=new GpuDelegate();
        // add gpuDelegate to option
        options.addDelegate(gpuDelegate);
        // now set number of threads to options
        options.setNumThreads(4); // set this according to your phone
        // this will load model weight to interpreter
        interpreter=new Interpreter(loadModelFile(assetManager,modelPath),options);
        // if model is load print
        Log.d("facial_Expression","Model is loaded");

        // now we will load haarcascade classifier
        try {
            // define input stream to read classifier
            InputStream is=context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            // create a folder
            File cascadeDir=context.getDir("cascade",Context.MODE_PRIVATE);
            // now create a new file in that folder
            File mCascadeFile=new File(cascadeDir,"haarcascade_frontalface_alt");
            // now define output stream to transfer data to file we created
            FileOutputStream os=new FileOutputStream(mCascadeFile);
            // now create buffer to store byte
            byte[] buffer=new byte[4096];
            int byteRead;
            // read byte in while loop
            // when it read -1 that means no data to read
            while ((byteRead=is.read(buffer)) !=-1){
                // writing on mCascade file
                os.write(buffer,0,byteRead);

            }


            // close input and output stream
            is.close();
            os.close();
            cascadeClassifier=new CascadeClassifier(mCascadeFile.getAbsolutePath());
            // if cascade file is loaded print
            Log.d("facial_Expression","Classifier is loaded");

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public Mat recognizeImage(Mat mat_image){

        //BURADAKİ mat veya bitmap boyutları, kişinin yüzünün etrafına çizilen karenin boyutlarıdır

        Core.flip(mat_image.t(),mat_image,1);// rotate mat_image by 90 degree
        // start with our process
        // convert mat_image to gray scale image
        Mat grayscaleImage=new Mat();
        Imgproc.cvtColor(mat_image,grayscaleImage,Imgproc.COLOR_RGBA2GRAY);
        // set height and width
        height=grayscaleImage.height();
        width=grayscaleImage.width();

        int absoluteFaceSize=(int)(height * 0.1);
        // now create MatofRect to store face
        MatOfRect faces=new MatOfRect();
        // check if cascadeClassifier is loaded or not
        if(cascadeClassifier !=null){
            //                                  input         output
            cascadeClassifier.detectMultiScale(grayscaleImage,faces,1.1,2,2,
                    new Size(absoluteFaceSize,absoluteFaceSize),new Size());
        }
        // now convert it to array
        Rect[] faceArray=faces.toArray();
        // loop through each face
        for (int i=0;i<faceArray.length;i++){
            // if you want to draw rectangle around face
            //                input/output starting point ending point        color   R  G  B  alpha    thickness
            Imgproc.rectangle(mat_image,faceArray[i].tl(),faceArray[i].br(),new Scalar(0,255,0,255),2);
            // now crop face from original frame and grayscaleImage
            // starting x coordinate       starting y coordinate
            Rect roi=new Rect((int)faceArray[i].tl().x,(int)faceArray[i].tl().y,
                    ((int)faceArray[i].br().x)-(int)(faceArray[i].tl().x),
                    ((int)faceArray[i].br().y)-(int)(faceArray[i].tl().y));
            // it's very important check one more time
            Mat cropped_rgba=new Mat(mat_image,roi);//
            // now convert cropped_rgba to bitmap
            Bitmap bitmap=null;
            bitmap=Bitmap.createBitmap(cropped_rgba.cols(),cropped_rgba.rows(),Bitmap.Config.ARGB_8888);

            Utils.matToBitmap(cropped_rgba,bitmap);
            // resize bitmap to (48,48)
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,48,48,false);
            // now convert scaledBitmap to byteBuffer
            ByteBuffer byteBuffer=convertBitmapToByteBuffer(scaledBitmap);
            System.out.println("byteBuffer :" + byteBuffer);
            // now create an object to hold output
            float[][] emotion=new float[1][1];
            //now predict with bytebuffer as an input and emotion as an output
            interpreter.run(byteBuffer,emotion);
            // if emotion is recognize print value of it

            // define float value of emotion
            float emotion_v=(float)Array.get(Array.get(emotion,0),0);
            Log.d("facial_expression","Output:  "+ emotion_v);
            // create a function that return text emotion
            String emotion_s=get_emotion_text(emotion_v);
            // now put text on original frame(mat_image)
            //             input/output    text: Angry (2.934234)
            Log.d("emotion","Output:  "+ emotion_s);


            Imgproc.putText(mat_image,emotion_s+" ("+emotion_v+")",
                    new Point((int)faceArray[i].tl().x+10,(int)faceArray[i].tl().y+20),
                    1,1.5,new Scalar(255,255,255,150),2);
            //      use to scale text      color     R G  B  alpha    thickness
        }
        // after prediction
        // rotate mat_image -90 degree
        Core.flip(mat_image.t(),mat_image,0);
        return mat_image;
    }

    private String get_emotion_text(float emotion_v) {
        // create an empty string
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