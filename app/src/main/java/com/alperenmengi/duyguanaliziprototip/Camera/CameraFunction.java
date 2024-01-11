package com.alperenmengi.duyguanaliziprototip.Camera;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class CameraFunction  implements CameraBridgeViewBase.CvCameraViewListener2{


    //Implementation for Camera
    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;
    // call java class
    private facialExpressionRecognition facialExpressionRecognition;




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
