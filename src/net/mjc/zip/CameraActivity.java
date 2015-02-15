package net.mjc.zip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.IOException;

public class CameraActivity extends Activity {

    private ActivityState state;
    private Camera camera;
    private CameraPreviewSurface previewSurface;

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
//            Utils.saveBitmap(getFilesDir(), state, );      // TODO
            Intent intent = new Intent();
            setResult(1, intent);
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        camera = getCameraInstance();

        previewSurface = new CameraPreviewSurface(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(previewSurface);

        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        camera.takePicture(null, null, mPicture);
                    }
                }
        );

        if (getIntent().getExtras() != null) {
            state = ActivityState.fromJson(getIntent().getExtras().getString(ActivityState.class.getName()));
            this.setTitle(state.getCurrentIdCheck().getDescription());
        }
    }

    public static Camera getCameraInstance(){
        try {
            return Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}