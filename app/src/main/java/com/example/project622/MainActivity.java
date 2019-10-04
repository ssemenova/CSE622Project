package com.example.project622;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private final int CAMERA_PERMISSION_REQUEST = 1;
    private CameraBridgeViewBase cameraView = null;

    private final String LOG_TAG = "622Project";

    // JNI
    private final native void adaptiveThresholdFromJNI(long var1, long var3);

    private final BaseLoaderCallback openCVLoaderCallback = new BaseLoaderCallback(this) {
        public void onManagerConnected(int status) {
            switch(status) {
                case 0:
                    Log.i("MainActivity", "OpenCV loaded successfully");
                    System.loadLibrary("native-lib");
                    if (MainActivity.this.cameraView != null) {
                        MainActivity.this.cameraView.enableView();
                    }

                    break;
                default:
                    super.onManagerConnected(status);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        this.cameraView = this.findViewById(R.id.main_surface);
        if (this.cameraView != null) {
            this.cameraView.setVisibility(SurfaceView.VISIBLE);
            this.cameraView.setCvCameraViewListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (cameraView != null) {
                        cameraView.setCameraPermissionGranted();
                    }
                } else {
                    String message = "Camera permission was not granted";
                    Log.e(LOG_TAG, message);
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat result = new Mat();
        this.adaptiveThresholdFromJNI(
                inputFrame.gray().getNativeObjAddr(),
                result.getNativeObjAddr()
        );
        return result;
    }

    protected void onPause() {
        super.onPause();
        if (this.cameraView != null) {
            this.cameraView.disableView();
        }
    }

    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            String message = "Internal OpenCV library not found.";
            Log.e(LOG_TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            Log.d(LOG_TAG, "OpenCV library found inside package. Using it!");
            openCVLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }


    @Override
    public void onCameraViewStarted(int width, int height) {}

    @Override
    public void onCameraViewStopped() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.cameraView != null) {
            this.cameraView.disableView();
        }
    }

}